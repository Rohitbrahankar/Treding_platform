package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Domain.VerificationType;
import com.treding_backend.tredingbackend.Modal.TwoFactorAuth;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Repository.UserRepository;
import com.treding_backend.tredingbackend.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if(user==null)
        {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) {
       User user = userRepository.findByEmail(email);

       if(user==null)
       {
           throw new UsernameNotFoundException("User not found");
       }


        return user;
    }

    @Override
    public User findUserById(Long userid) {

        Optional<User> user = userRepository.findById(userid);
        if(user.isEmpty())
        {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }



    @Override
    public User  enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setIsEnabled(true);
        twoFactorAuth.setSendTo(verificationType);


        user.setTwoFactorAuth(twoFactorAuth);

        return userRepository.save(user);

    }



    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);

        return userRepository.save(user);
    }
}
