package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long> {

    PaymentDetails findByUserId(Long userId);
}
