package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.PaymentDetails;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Repository.PaymentDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements  PaymentDetailService{
    @Autowired
    private PaymentDetailsRepo paymentDetailsRepo;
    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifscCode, String bankName, User user) {
       PaymentDetails paymentDetails = new PaymentDetails();
       paymentDetails.setAccountNumber(accountNumber);
       paymentDetails.setAcoountHolderName(accountHolderName);
       paymentDetails.setBankName(bankName);
       paymentDetails.setIfscCode(ifscCode);
       paymentDetails.setUser(user);

        return  paymentDetailsRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepo.findByUserId(user.getId());
    }
}
