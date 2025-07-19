//package com.treding_backend.tredingbackend.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationProperties(prefix = "jwt")
//public class JwtProperties {
//
//    private String secret;
//    private long expiration;
//
//    // Getter & Setter
//    public String getSecret() {
//        return secret;
//    }
//
//    public void setSecret(String secret) {
//        this.secret = secret;
//    }
//
//    public static long getExpiration() {
//        return expiration;
//    }
//
//    public void setExpiration(long expiration) {
//        this.expiration = expiration;
//    }
//}
//


package com.treding_backend.tredingbackend.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.treding_backend.tredingbackend.Modal.TwoFactorAuth;
import jakarta.persistence.Embedded;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")



public class JwtProperties {
    private static String secret;
    private static long expiration;

    @JsonProperty("twoFactorAuth")
    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    public static String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public static long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
