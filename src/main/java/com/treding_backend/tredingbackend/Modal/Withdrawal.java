package com.treding_backend.tredingbackend.Modal;

import com.treding_backend.tredingbackend.Domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private  long  id;
     private WithdrawalStatus status;

     private Long amount;


     @ManyToOne
     private  User user;
     private  LocalDateTime date = LocalDateTime.now();

}
