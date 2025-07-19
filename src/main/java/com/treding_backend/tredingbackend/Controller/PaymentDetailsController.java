package com.treding_backend.tredingbackend.Controller;

import com.treding_backend.tredingbackend.Modal.PaymentDetails;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Service.PaymentDetailService;
import com.treding_backend.tredingbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentDetailsController {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentDetailService paymentDetailService;

    @PostMapping("/payment-details")

    public ResponseEntity<PaymentDetails> addPaymentDetails(
            @RequestBody PaymentDetails paymentDetailsRequest,
            @RequestHeader("Autowired") String jwt ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails= paymentDetailService.addPaymentDetails(
                paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getAcoountHolderName(),
                paymentDetailsRequest.getIfscCode(),
                paymentDetailsRequest.getBankName(),
                user
        );
        return  new ResponseEntity<>(paymentDetails , HttpStatus.CREATED);

    }

    @GetMapping("/payment-details")
    public  ResponseEntity<PaymentDetails>getUsersPaymentDetails(
            @RequestHeader("Authorization") String jwt )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        PaymentDetails paymentDetails= paymentDetailService.getUsersPaymentDetails(user);
         return  new ResponseEntity<>(paymentDetails , HttpStatus.CREATED);

    }


}
