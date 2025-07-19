package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRepo extends JpaRepository<Withdrawal, Long> {

    List<Withdrawal> findByUserId(Long userId);

}
