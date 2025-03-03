package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Domain.VerificationType;
import com.treding_backend.tredingbackend.Modal.ForgetPasswordToken;
import com.treding_backend.tredingbackend.Modal.User;

public interface ForgetPasswordService {

    ForgetPasswordToken createToken(User user,
                                    String id, String otp,
                                    VerificationType verificationType,
                                    String sendTo);


    ForgetPasswordToken findById(String id);
    ForgetPasswordToken findByUser(Long userId) ;

    void  deleteToken(ForgetPasswordToken token);


}
