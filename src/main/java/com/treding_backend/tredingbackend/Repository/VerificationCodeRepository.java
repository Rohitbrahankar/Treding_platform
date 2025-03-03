package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {

    public VerificationCode findByUserId(Long userId);
}
