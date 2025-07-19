package com.treding_backend.tredingbackend.Service;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.model.checkout.SessionCollection;
import com.stripe.param.checkout.SessionCreateParams;
import com.treding_backend.tredingbackend.Domain.PaymentMethod;
import com.treding_backend.tredingbackend.Domain.PaymentOrderStatus;
import com.treding_backend.tredingbackend.Modal.PaymentOrder;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Repository.PaymentOrderRepo;
import com.treding_backend.tredingbackend.Response.PaymentResponce;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl  implements  PaymentService{
    @Autowired
    private PaymentOrderRepo paymentOrderRepo;

    @Value("${stripe.api.key}")
    private  String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private  String apiKey;

    @Value("${razorpay.api.secret}")
    private  String apiSecretKey;
    @Override
    public PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentmethod) {
         PaymentOrder paymentOrder = new PaymentOrder();
         paymentOrder.setUser(user);
         paymentOrder.setAmount(amount);
         paymentOrder.setPaymentMethod(paymentmethod);

        return paymentOrderRepo.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {


        return paymentOrderRepo.findById(id).orElseThrow(
                ()-> new  Exception("payment order not fount"));
    }

    @Override
    public Boolean ProccedPaymentOrder(PaymentOrder paymentOrder, String PaymentId) throws RazorpayException {


        if(paymentOrder.getStatus() .equals(PaymentOrderStatus.PENDING))
        {
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY))
            {
                RazorpayClient razorpayClient= new RazorpayClient(apiKey, apiSecretKey);
                Payment payment = razorpayClient.payments.fetch(PaymentId);
                Integer amount= payment.get("amount");

                String status = payment.get("status");

                if(status .equals("captured"))
                {
                     paymentOrder.setStatus(PaymentOrderStatus.SUCCESSED);
                     return  true;
                }else{
                    paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                    paymentOrderRepo.save(paymentOrder);
                    return false;
                }



            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESSED);

            paymentOrderRepo.save(paymentOrder);
            return true;

        }
        return false;
    }

    @Override
    public PaymentResponce createRazorpayPaymentLing(User user, Long amount) {

       Long Amount = amount  * 100;

         try{
             RazorpayClient razorpay = new RazorpayClient(apiKey , apiSecretKey);

             JSONObject paymentLinkRequest= new JSONObject();

             paymentLinkRequest .put("amount", amount);
             paymentLinkRequest.put("currency ", "INR");

             JSONObject customer = new JSONObject();
             customer.put("name" , user.getFullName());

             customer.put("email", user.getEmail());
             paymentLinkRequest.put("customer", customer);

             JSONObject notify = new JSONObject();
             notify.put("email", true);

             //set remainder setting
             paymentLinkRequest.put("notify", notify);

             paymentLinkRequest .put("reminder_enable", true);


             //set the callback url and method

             paymentLinkRequest.put("callback_url","http://localhost:5173/wallet");
             paymentLinkRequest.put("callback_method","get");

             // Create the payment

             PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);
             String paymentLinkId =paymentLink.get("id");
             String paymentLinkUrl = paymentLink.get("short_URL");

             PaymentResponce res = new PaymentResponce();

             res.setPayment_Url(paymentLinkUrl);
             return res;

         } catch (RazorpayException e) {

             System.out.println("Error create  payment Link"+e.getMessage());
             throw new RuntimeException(e);
         }


    }

    @Override
    public PaymentResponce createStripPaymentLing(User user, Long amount, Long OrderId) throws StripeException {
        Stripe.apiKey= stripeSecretKey;

        SessionCreateParams params= SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/wallet?order_id="+OrderId)
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("USD")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("Top up wallet")
                                        .build()
                                ).build()
                        ).build()
                ).build();
        Session session = Session.create(params);
        System.out.println("Session _______"+session);

        PaymentResponce res = new PaymentResponce();
        res.setPayment_Url(session.getUrl());

        return res;


    }
}
