package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgetPasswordRepo extends JpaRepository<ForgetPasswordToken ,String> {

    ForgetPasswordToken findByUserId(Long userId);

}
