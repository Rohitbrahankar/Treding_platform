package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {

   List<Order> findByUserId(Long userId);

}
