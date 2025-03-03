package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.TwoFactorAuth;
import com.treding_backend.tredingbackend.Modal.TwofactorOtp;
import com.treding_backend.tredingbackend.Modal.User;

public interface TwoFactorOtpService {

    TwofactorOtp createTwofactorOtp(User user, String otp , String jwt);

    TwofactorOtp findByUser(Long userId);
    TwofactorOtp findById(String id);

    boolean verifyTwofactorOtp(TwofactorOtp twofactorOtp,String otp);

    void  deteleTwoFactorOtp(TwofactorOtp twofactorOtp);
}
