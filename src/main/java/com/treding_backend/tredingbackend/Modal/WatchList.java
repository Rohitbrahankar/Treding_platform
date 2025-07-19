package com.treding_backend.tredingbackend.Modal;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @OneToOne
    private  User user;


    @ManyToMany
    private List<Coins> coins= new ArrayList<>();



}
