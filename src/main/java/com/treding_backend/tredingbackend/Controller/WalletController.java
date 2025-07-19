package com.treding_backend.tredingbackend.Controller;

import com.treding_backend.tredingbackend.Modal.*;
import com.treding_backend.tredingbackend.Response.PaymentResponce;
import com.treding_backend.tredingbackend.Service.OrderService;
import com.treding_backend.tredingbackend.Service.PaymentService;
import com.treding_backend.tredingbackend.Service.UserService;
import com.treding_backend.tredingbackend.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;





//    @GetMapping("/api/wallet")
//    public ResponseEntity<Wallet>getUserWallet(@RequestHeader("Authorization") String jwt){
//
//        User user=  userService.findUserProfileByJwt(jwt);
//
//        Wallet wallet= walletService.getUserWallet(user);
//
//        return  new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);
//    }

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwtHeader) {
        if (!jwtHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = jwtHeader.substring(7); // remove "Bearer "

        User user = userService.findUserProfileByJwt(token);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }


    @PutMapping("/api/wallet/{walletId}/transfer")
    public  ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long  walletId,
            @RequestBody WalletTransaction req) throws Exception {

            User senderUser = userService.findUserProfileByJwt(jwt);
            Wallet  receiverWallet= walletService.findWalletById(walletId);
            Wallet wallet = walletService.walletTowalletTransfer(senderUser , receiverWallet , req.getAmount());
            return  new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);


    }


    @PutMapping("/api/wallet/order/{orderId}/pay")
    public  ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long  orderId) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Order  order = orderService.getOrderById(orderId);


        Wallet wallet = walletService.payOrderPayment(order, user);
        return  new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);


    }



    @PutMapping("/api/wallet/deposit")
    public  ResponseEntity<Wallet> addBalanceToWallet(
            @RequestHeader("Authorization")String jwt,

            @RequestParam(name="orderId")Long orderId ,
            @RequestParam(name = "payment_id")String paymentId  ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);



        Wallet wallet = walletService.getUserWallet(user);

        PaymentOrder order = paymentService.getPaymentOrderById(orderId);
        Boolean status = paymentService.ProccedPaymentOrder(order , paymentId);
//        PaymentResponce responce= new PaymentResponce();
//        responce.setPayment_Url("Deposit Success");

        if(status)
        {
             wallet= walletService.addBalance(wallet, order.getAmount());

        }

        return  new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);


    }



}
