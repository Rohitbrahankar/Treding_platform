package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Domain.OrderType;
import com.treding_backend.tredingbackend.Modal.Order;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.Wallet;
import com.treding_backend.tredingbackend.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
   @Autowired
   private WalletRepository walletRepository;






    @Override
    public Wallet getUserWallet(User user) {

        Wallet wallet= walletRepository.findByUserId(user.getId());
        if(wallet==null)
        {
            wallet = new Wallet();
            wallet.setUser(user);
        }


        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {

       BigDecimal balance = wallet.getBalance();
       BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
       wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet>wallet = walletRepository.findById(id);
        if(wallet.isPresent())
        {
            return wallet.get();
        }
        throw new Exception("Wallet not found");
    }

    @Override
    public Wallet walletTowalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {

        Wallet senderwallet= getUserWallet(sender);
        if(senderwallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0)
        {
               throw new Exception("Insufficient Balance");
        }
        BigDecimal senderBalance = senderwallet
                .getBalance()
                .subtract(BigDecimal.valueOf(amount));
        senderwallet.setBalance(senderBalance);
        walletRepository.save(senderwallet);

        BigDecimal receiverBalance = receiverWallet.getBalance();
        BigDecimal  recivedBalance =receiverBalance.add(BigDecimal.valueOf(amount));

        receiverWallet.setBalance(recivedBalance);
        walletRepository.save(receiverWallet);
        return senderwallet;

    }



    // getting paymet and deduct payment
    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        if(order.getOrderType().equals(OrderType.BUY))
        {
            BigDecimal newbalance = wallet.getBalance().subtract(order.getPrice());
            if(newbalance.compareTo(order.getPrice()) < 0)
            {
                throw new Exception("Insufficient funds for this transactions");
            }
            wallet.setBalance(newbalance);

        }
       else
        {
            BigDecimal newbalance = wallet.getBalance().add(order.getPrice());

            wallet.setBalance(newbalance);

        }
     walletRepository.save(wallet);
        return wallet;
    }
}
