package com.treding_backend.tredingbackend.Controller;


import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.treding_backend.tredingbackend.Domain.PaymentMethod;
import com.treding_backend.tredingbackend.Modal.PaymentOrder;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Response.PaymentResponce;
import com.treding_backend.tredingbackend.Service.PaymentService;
import com.treding_backend.tredingbackend.Service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {
     @Autowired
    private UserService userService;

     @Autowired
     private PaymentService paymentService;


     @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
     public ResponseEntity<PaymentResponce> paymentHandler(
             @PathVariable PaymentMethod paymentMethod,
             @PathVariable Long amount,
             @RequestHeader("Authorization")String jwt ) throws Exception , RazorpayException , StripeException {

         User user = userService.findUserProfileByJwt(jwt);

         PaymentResponce paymentResponce;

         PaymentOrder order = paymentService.createPaymentOrder(user , amount , paymentMethod);


         if(paymentMethod.equals(PaymentMethod.RAZORPAY))
         {
             paymentResponce = paymentService.createRazorpayPaymentLing(user , amount);

         }else{
             paymentResponce= paymentService.createStripPaymentLing(user , amount, order.getId());

         }
         return  new ResponseEntity<>(paymentResponce, HttpStatus.CREATED);

     }

}
