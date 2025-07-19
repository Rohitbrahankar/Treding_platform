package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.Order;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user) ;
    Wallet addBalance(Wallet wallet,Long money);

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletTowalletTransfer(User sender , Wallet receiverWallet,Long amount) throws Exception;

    Wallet payOrderPayment(Order order , User user) throws Exception;


}
