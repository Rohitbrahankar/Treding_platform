//package com.treding_backend.tredingbackend.Controller;
//
//import com.treding_backend.tredingbackend.Domain.VerificationType;
//import com.treding_backend.tredingbackend.Request.ForgetPasswordTokenRequest;
//import com.treding_backend.tredingbackend.Modal.ForgetPasswordToken;
//import com.treding_backend.tredingbackend.Modal.User;
//import com.treding_backend.tredingbackend.Modal.VerificationCode;
//import com.treding_backend.tredingbackend.Request.ResetPasswordRequest;
//import com.treding_backend.tredingbackend.Response.ApiResponse;
//import com.treding_backend.tredingbackend.Response.AuthResponse;
//import com.treding_backend.tredingbackend.Service.EmailService;
//import com.treding_backend.tredingbackend.Service.ForgetPasswordService;
//import com.treding_backend.tredingbackend.Service.UserService;
//import com.treding_backend.tredingbackend.Service.Verificationcodeservice;
//import com.treding_backend.tredingbackend.Utils.otpUtils;
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.UUID;
//
//@RestController
//public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private EmailService emailService;
//
//
//@Autowired
//    private Verificationcodeservice verificationcodeservice;
//@Autowired
//private ForgetPasswordService forgetPasswordService;
//
////
//    @GetMapping("/api/users/profile")
//    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization ")String authHeader) {
//
//        String jwt = authHeader.replace("Bearer ", "");
//        User user= userService.findUserProfileByJwt(jwt);
//
//        return  new ResponseEntity<>(user, HttpStatus.OK);
//
//    }
//
////    @GetMapping("/api/user/profile")
////    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String authHeader) {
////        String jwt = authHeader.replace("Bearer ", ""); // remove prefix if present
////        User user = userService.findUserProfileByJwt(jwt);
////        return ResponseEntity.ok(user);
////    }
//
//
//
//    @PostMapping("/api/users/verification/{verificationType}/send-otp")
// public ResponseEntity<String> sendVerificationOtp(
//         @RequestHeader("Authorization") String  jwt,
//         @PathVariable VerificationType verificationType
// ) throws MessagingException {
//     User user = userService.findUserProfileByJwt(jwt);
//     VerificationCode verificationCode= verificationcodeservice.getVerificationCodeByUser(user.getId());
//
//     if(verificationCode==null) {
//         verificationCode=verificationcodeservice
//                 .sendVerificationCode(user, verificationType);
//     }
//
//  if(verificationType.equals(verificationType.EMAIL))
//  {
//      emailService.sendverificationOtpEmail(user.getEmail(), verificationCode.getOtp() );
//  }
//
//     return  new ResponseEntity<>("Verification otp send successfully ", HttpStatus.OK);
//
// }
//
//
//    /* use to verify otp */
//    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
//
//    public ResponseEntity<User> enableTwoFactorAuthentication(
//            @PathVariable String otp,
//            @RequestHeader("Authorization") String jwt) {
//
//        // Fetch user by JWT
//        User user = userService.findUserProfileByJwt(jwt);
//
//        // Fetch verification code
//        VerificationCode verificationCode = verificationcodeservice.getVerificationCodeByUser(user.getId());
//
//        // Check if verificationCode exists
//        if (verificationCode == null || verificationCode.getOtp() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No OTP found for the user");
//        }
//
//        // Validate OTP
//        if (!verificationCode.getOtp().equals(otp)) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid OTP");
//        }
//
//        // Determine the recipient (email or mobile)
//        String sendTo = (verificationCode.getVerificationType() == VerificationType.EMAIL)
//                ? verificationCode.getEmail()
//                : verificationCode.getMobile();
//
//        // Enable two-factor authentication
//        User updatedUser = userService.enableTwoFactorAuthentication(
//                verificationCode.getVerificationType(), sendTo, user);
//
//        // Remove the verification code after successful verification
//        verificationcodeservice.deleteVerificationCodeById(verificationCode);
//
//        return ResponseEntity.ok(updatedUser);
//    }
//
//
////send otp of pass forget
//    @PostMapping("/auth/users/reset-password/send-otp")
//    public ResponseEntity<AuthResponse> sendForgetPasswordOtp(
//
//            @RequestBody ForgetPasswordTokenRequest req
//            ) throws MessagingException {
//
//        User user = userService.findUserByEmail(req.getSendTo());
//        String otp = otpUtils.generateOtp();
//        UUID uuid = UUID.randomUUID();
//        String id = uuid.toString();
//
//        ForgetPasswordToken token = forgetPasswordService.findByUser(user.getId());
//
//        if (token == null) {
//            token = forgetPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
//        }
//
//        AuthResponse response = null;
//        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
//            emailService.sendverificationOtpEmail(// send email  with otp
//                    user.getEmail(),
//                    token.getOtp()
//            );
//
//            response = new AuthResponse();
//            response.setSession(token.getId());
//            response.setMessage("Password reset otp sent successfully ");
//
//
//        }
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//
//    }
//
//
//    @PatchMapping("/auth/users/reset-password/verify-otp")
//
//    public ResponseEntity<ApiResponse> resetPassword(
//            @RequestParam String id,
//            @RequestBody ResetPasswordRequest req,
//            @RequestHeader("Authorization") String jwt) throws Exception {
//
//        // Fetch user by JWT
//        User user = userService.findUserProfileByJwt(jwt);
//
//        // Fetch verification code
//        ForgetPasswordToken forgetPasswordToken = forgetPasswordService.findById(id);
//
//        boolean isverified=forgetPasswordToken.getOtp().equals(req.getOtp());//use to verify otp
//
//        if(isverified) {
//            userService.updatePassword(forgetPasswordToken.getUser(),req.getPassword());
//            ApiResponse res= new ApiResponse();//class calling
//            res.setMessage("Password update successfully");
//
//            return  new ResponseEntity<>(res,HttpStatus.ACCEPTED);
//        }
//        throw new Exception("wrong otp ");
//
//    }
//
//
//
//}


package com.treding_backend.tredingbackend.Controller;

import com.treding_backend.tredingbackend.Domain.VerificationType;
import com.treding_backend.tredingbackend.Request.ForgetPasswordTokenRequest;
import com.treding_backend.tredingbackend.Request.ResetPasswordRequest;
import com.treding_backend.tredingbackend.Response.ApiResponse;
import com.treding_backend.tredingbackend.Response.AuthResponse;
import com.treding_backend.tredingbackend.Modal.ForgetPasswordToken;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.VerificationCode;
import com.treding_backend.tredingbackend.Service.EmailService;
import com.treding_backend.tredingbackend.Service.ForgetPasswordService;
import com.treding_backend.tredingbackend.Service.UserService;
import com.treding_backend.tredingbackend.Service.Verificationcodeservice;
import com.treding_backend.tredingbackend.Utils.otpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Verificationcodeservice verificationcodeservice;

    @Autowired
    private ForgetPasswordService forgetPasswordService;

//    // ✅ Get User Profile
//    @GetMapping("/api/users/profile")
//    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) {
//        //String jwt = authHeader.replace("Bearer ", "");
//        User user = userService.findUserProfileByJwt(jwt);
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwtHeader) {
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtHeader.substring(7); // Removes "Bearer "

        User user = userService.findUserProfileByJwt(token);

        return ResponseEntity.ok(user);
    }


    // ✅ Send Verification OTP
    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType
    ) throws MessagingException {
        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationcodeservice.getVerificationCodeByUser(user.getId());

        if (verificationCode == null) {
            verificationCode = verificationcodeservice.sendVerificationCode(user, verificationType);
        }

        if (verificationType == VerificationType.EMAIL) {
            emailService.sendverificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }

        return ResponseEntity.ok("Verification OTP sent successfully");
    }

    // ✅ Verify OTP and Enable Two-Factor Auth
    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) {

        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationcodeservice.getVerificationCodeByUser(user.getId());

        if (verificationCode == null || verificationCode.getOtp() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No OTP found for the user");
        }

        if (!verificationCode.getOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid OTP");
        }

        String sendTo = verificationCode.getVerificationType() == VerificationType.EMAIL
                ? verificationCode.getEmail()
                : verificationCode.getMobile();

        User updatedUser = userService.enableTwoFactorAuthentication(
                verificationCode.getVerificationType(), sendTo, user);

        verificationcodeservice.deleteVerificationCodeById(verificationCode);

        return ResponseEntity.ok(updatedUser);
    }

    // ✅ Send OTP for Password Reset
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgetPasswordOtp(
            @RequestBody ForgetPasswordTokenRequest req
    ) throws MessagingException {

        User user = userService.findUserByEmail(req.getSendTo());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with this email");
        }

        String otp = otpUtils.generateOtp();
        String id = UUID.randomUUID().toString();

        ForgetPasswordToken token = forgetPasswordService.findByUser(user.getId());
        if (token == null) {
            token = forgetPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
        }

        if (req.getVerificationType() == VerificationType.EMAIL) {
            emailService.sendverificationOtpEmail(user.getEmail(), token.getOtp());
        }

        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset OTP sent successfully");

        return ResponseEntity.ok(response);
    }

    // ✅ Reset Password Using OTP
    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req,
            @RequestHeader("Authorization") String jwt) {

        User user = userService.findUserProfileByJwt(jwt);
        ForgetPasswordToken forgetPasswordToken = forgetPasswordService.findById(id);

        if (forgetPasswordToken == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired session ID");
        }

        if (!forgetPasswordToken.getOtp().equals(req.getOtp())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong OTP");
        }

        userService.updatePassword(forgetPasswordToken.getUser(), req.getPassword());

        ApiResponse res = new ApiResponse();
        res.setMessage("Password updated successfully");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
}

