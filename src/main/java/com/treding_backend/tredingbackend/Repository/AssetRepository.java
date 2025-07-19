package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository  extends JpaRepository<Asset , Long> {

    List<Asset> findByUserId(Long userId);

    Asset findByUserIdAndCoinsId(Long userId, String coinId  );
}
