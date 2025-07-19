package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.PaymentDetails;
import com.treding_backend.tredingbackend.Modal.User;

public interface PaymentDetailService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifscCode,
                                            String bankName,
                                           User user);
    public  PaymentDetails getUsersPaymentDetails(User user);
}
