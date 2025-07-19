package com.treding_backend.tredingbackend.Modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private  double quantity;

    @ManyToOne
    public Coins coins;
    private  double buyprice;
    private  double sellprice;


//    @JsonIgnore
//    @OneToOne(mappedBy = "orderItem")
//    private Order order;
@OneToOne
@JoinColumn(name = "order")
@JsonIgnore
private Order order;




}
