package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepo extends JpaRepository<WatchList , Long> {
    WatchList findByUserId(Long userId);
}
