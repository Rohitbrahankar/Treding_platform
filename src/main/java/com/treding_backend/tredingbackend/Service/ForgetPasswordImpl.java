package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Domain.VerificationType;
import com.treding_backend.tredingbackend.Modal.ForgetPasswordToken;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Repository.ForgetPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgetPasswordImpl implements ForgetPasswordService {
   @Autowired
   private ForgetPasswordRepo forgetPasswordRepo;

    @Override
    public ForgetPasswordToken createToken(User user,
                                           String id,
                                           String otp,
                                           VerificationType verificationType,
                                           String sendTo) {

       ForgetPasswordToken token= new ForgetPasswordToken();
       token.setUser(user);
       token.setSendTo(sendTo);
       token.setVerificationType(verificationType);
       token.setOtp(otp);
       token.setId(id);



        return forgetPasswordRepo.save(token);
    }

    @Override
    public ForgetPasswordToken findById(String id) {
        Optional<ForgetPasswordToken> token= forgetPasswordRepo.findById(id);
            return token.orElse(null);//if token is present return token else return null
    }

    @Override
    public ForgetPasswordToken findByUser(Long userId) {
        return forgetPasswordRepo.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgetPasswordToken token) {
        forgetPasswordRepo.delete(token);

    }
}
