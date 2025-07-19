package com.treding_backend.tredingbackend.Modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private  String accountNumber;
    private  String acoountHolderName;
    private  String ifscCode;
    private  String  bankName;


    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  User user;

}
