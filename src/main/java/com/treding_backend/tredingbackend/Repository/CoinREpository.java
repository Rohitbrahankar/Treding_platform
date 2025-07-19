package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.Coins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinREpository extends JpaRepository<Coins,String> {

}
