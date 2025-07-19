package com.treding_backend.tredingbackend.Controller;

import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.Wallet;
import com.treding_backend.tredingbackend.Modal.WalletTransaction;
import com.treding_backend.tredingbackend.Modal.Withdrawal;
import com.treding_backend.tredingbackend.Service.UserService;
import com.treding_backend.tredingbackend.Service.WalletService;
import com.treding_backend.tredingbackend.Service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController

public class WithdrawalControler {
    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requesWithdrawal(amount, user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }


        @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
                public ResponseEntity<?> proceedWithdrawal(
                        @PathVariable Long id,
                        @PathVariable boolean accept,
                        @RequestHeader("Authorization")
                        String jwt  ) throws  Exception {

             User user = userService.findUserProfileByJwt(jwt );

             Withdrawal withdrawal = withdrawalService.procideWithdrawal(id , accept);
             Wallet userWallet = walletService.getUserWallet(user);
             if(!accept)
             {
                 walletService.addBalance(userWallet, -withdrawal.getAmount());
             }

             return  new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }

    @GetMapping("/api/withdrawal")

    public  ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
         @RequestHeader("Authorization") String jwt ) throws  Exception {
        User user = userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal = withdrawalService.getUserWithdrawalHistory(user);
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    public ResponseEntity<List<Withdrawal>> getWithdrawalRequest(
            @RequestHeader("Authorization") String  jwt) throws  Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequest();
        return  new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }


}
