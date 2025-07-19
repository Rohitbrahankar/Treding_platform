package com.treding_backend.tredingbackend.Modal;//package com.treding_backend.tredingbackend.Modal;
//
import com.treding_backend.tredingbackend.Domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;
//
//
//
//@Data
//@Embeddable
//public class TwoFactorAuth {
//    private Boolean isEnabled=false;
//    private VerificationType sendTo;
//
//
//
//}


//import com.treding_backend.tredingbackend.Domain.VerificationType;
//import jakarta.persistence.Embeddable;
//import lombok.Data;
//
//@Data
//@Embeddable
//public class TwoFactorAuth {
//
//    @Column(name ="is_enabled")
//    private Boolean isEnabled;
//    @Enumerated(EnumType.STRING)
//    private VerificationType sendTo;
//
//        // Getters and Setters
//        public Boolean getIsEnabled() {
//            return isEnabled;
//        }
//
//        public void setIsEnabled(Boolean enabled) {
//            isEnabled = enabled;
//        }
//
//        public VerificationType getSendTo() {
//            return sendTo;
//        }
//
//        public void setSendTo(VerificationType sendTo) {
//            this.sendTo = sendTo;
//        }
//    }




import com.treding_backend.tredingbackend.Domain.VerificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class TwoFactorAuth {

    @Column(name = "is_enabled")
    private Boolean isEnabled = false;

    @Enumerated(EnumType.STRING)
    private VerificationType sendTo;

    public Boolean getIsEnabled() {
        return isEnabled != null ? isEnabled : false;
    }

    public void setIsEnabled(Boolean enabled) {
        this.isEnabled = enabled;
    }

    public VerificationType getSendTo() {
        return sendTo;
    }

    public void setSendTo(VerificationType sendTo) {
        this.sendTo = sendTo;
    }
}

