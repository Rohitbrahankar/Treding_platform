package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Domain.WithdrawalStatus;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.Withdrawal;
import com.treding_backend.tredingbackend.Repository.WithdrawalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class Withdrawalserviceimpl implements  WithdrawalService{
    @Autowired
    private WithdrawalRepo withdrawalRepo;

    @Override
    public Withdrawal requesWithdrawal(Long amount, User user) {

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);

        return withdrawalRepo.save(withdrawal)  ;

    }

    @Override
    public Withdrawal procideWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal = withdrawalRepo.findById(withdrawalId);
        if(withdrawal.isEmpty()) {
            throw  new Exception("Withdrawal not found ");
        }
        Withdrawal withdrawal1 = withdrawal.get();
        withdrawal1.setDate(LocalDateTime.now());
        if(accept){
            withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
        }else{
            withdrawal1.setStatus(WithdrawalStatus.PENDING);
        }
        return withdrawalRepo.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUserWithdrawalHistory(User user) {
        return  withdrawalRepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepo.findAll();
    }
}
