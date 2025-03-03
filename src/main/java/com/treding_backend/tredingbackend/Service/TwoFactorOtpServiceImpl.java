package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.TwoFactorAuth;
import com.treding_backend.tredingbackend.Modal.TwofactorOtp;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Repository.TwoFactorOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {
    @Autowired
    private TwoFactorOtpRepository twoFactorOtpRepository;




    @Override
    public TwofactorOtp createTwofactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();// use to generate random unique identifier


        String id= uuid.toString();
        TwofactorOtp twoFactorOtp = new TwofactorOtp();
        twoFactorOtp.setOtp(otp);
        twoFactorOtp.setJwt(jwt);
        twoFactorOtp.setId(id);
        twoFactorOtp.setUser(user);
       return twoFactorOtpRepository.save(twoFactorOtp);

    }

    @Override
    public TwofactorOtp findByUser(Long userId) {


        return twoFactorOtpRepository.findByUserId(userId);
    }

    @Override
    public TwofactorOtp findById(String id) {
        Optional<TwofactorOtp> opt= twoFactorOtpRepository.findById(id);
        /* Why use Optional?
            To avoid NullPointerException if the OTP does not exist.
            Provides built-in methods to handle missing values safely. */
        return opt.orElse(null);
    }


    @Override
    public boolean verifyTwofactorOtp(TwofactorOtp twofactorOtp, String otp) {
        Object twoFactorOtp;
        return twofactorOtp.getOtp().equals(otp);
    }




    @Override
    public void deteleTwoFactorOtp(TwofactorOtp twofactorOtp) {

        twoFactorOtpRepository.delete(twofactorOtp);


    }
}
