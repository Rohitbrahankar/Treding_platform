package com.treding_backend.tredingbackend.Request;

import com.treding_backend.tredingbackend.Domain.VerificationType;
import lombok.Data;

@Data
public class ForgetPasswordTokenRequest {

    private String sendTo;
    private VerificationType verificationType;
}
