package com.treding_backend.tredingbackend.Service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.treding_backend.tredingbackend.Domain.PaymentMethod;
import com.treding_backend.tredingbackend.Modal.PaymentOrder;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Response.PaymentResponce;

public interface PaymentService {
    PaymentOrder createPaymentOrder(User user , Long amount , PaymentMethod paymentmethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean ProccedPaymentOrder(PaymentOrder paymentOrder, String PaymentId) throws RazorpayException;

    PaymentResponce createRazorpayPaymentLing(User user , Long amount );

    PaymentResponce createStripPaymentLing(User user , Long amount ,Long OrderId) throws StripeException;


}
