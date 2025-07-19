package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
