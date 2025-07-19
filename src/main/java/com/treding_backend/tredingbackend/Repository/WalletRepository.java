package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet , Long> {

    Wallet findByUserId(Long userId);

}
