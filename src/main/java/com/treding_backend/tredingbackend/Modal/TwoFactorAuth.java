package com.treding_backend.tredingbackend.Modal;

import com.treding_backend.tredingbackend.Domain.VerificationType;
import lombok.Data;



@Data
public class TwoFactorAuth {
    private Boolean isEnabled=false;
    private VerificationType sendTo;



}
