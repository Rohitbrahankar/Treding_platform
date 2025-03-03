package com.treding_backend.tredingbackend.Modal;


import com.treding_backend.tredingbackend.Domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Entity
@Data
public class ForgetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private User user;
    private String otp;

    private VerificationType verificationType; //mobile or email

    private  String  sendTo;


}
