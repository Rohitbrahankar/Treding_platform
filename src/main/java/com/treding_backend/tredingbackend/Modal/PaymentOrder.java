package com.treding_backend.tredingbackend.Modal;

import com.treding_backend.tredingbackend.Domain.PaymentMethod;
import com.treding_backend.tredingbackend.Domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private  Long amount;
    private PaymentOrderStatus status;
    private PaymentMethod paymentMethod;
    @ManyToOne
    private  User user;


}
