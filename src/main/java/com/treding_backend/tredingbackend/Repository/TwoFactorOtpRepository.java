package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.TwofactorOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwofactorOtp, String > {
    TwofactorOtp findByUserId(Long userId);

}
