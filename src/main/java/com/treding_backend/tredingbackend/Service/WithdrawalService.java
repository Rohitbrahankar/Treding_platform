package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.Withdrawal;

import java.util.List;

public interface WithdrawalService {

    Withdrawal requesWithdrawal(Long amount , User user);
    Withdrawal procideWithdrawal(Long withdrawalId , boolean accept) throws Exception;
    List<Withdrawal> getUserWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequest();

}
