package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Domain.VerificationType;
import com.treding_backend.tredingbackend.Modal.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    public User  findUserProfileByJwt(String jwt);
    public  User findUserByEmail(String email);
    public  User findUserById(Long userid);

    @Transactional

    public  User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);

    User updatePassword(User user,String newPassword);



}
