package com.treding_backend.tredingbackend.Modal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double quantity;
    private double buyPrice;

    @ManyToOne
//    @JoinColumn(name = "coin_id") // optional but recommended
    private  Coins coins;


    @ManyToOne
//    @JoinColumn(name = "user_id") // optional but recommended
    private User user;
}
