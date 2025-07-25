package com.treding_backend.tredingbackend.Modal;

import com.treding_backend.tredingbackend.Domain.OrderStatus;
import com.treding_backend.tredingbackend.Domain.OrderType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;


    @ManyToOne
    private User user ;


    @Column(nullable = false) // filed is req without this field order is not created
    private OrderType orderType;

    @Column(nullable = false)
    private BigDecimal price;

    private LocalDateTime  timeStamp=LocalDateTime.now();

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;


}
