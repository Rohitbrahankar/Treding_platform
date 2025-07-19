package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepo  extends JpaRepository<PaymentOrder , Long> {

}
