package com.treding_backend.tredingbackend.Controller;

import com.treding_backend.tredingbackend.Domain.VerificationType;
import com.treding_backend.tredingbackend.Request.ForgetPasswordTokenRequest;
import com.treding_backend.tredingbackend.Modal.ForgetPasswordToken;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.VerificationCode;
import com.treding_backend.tredingbackend.Request.ResetPasswordRequest;
import com.treding_backend.tredingbackend.Response.ApiResponse;
import com.treding_backend.tredingbackend.Response.AuthResponse;
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

    private String jwt;
@Autowired
    private Verificationcodeservice verificationcodeservice;
@Autowired
private ForgetPasswordService forgetPasswordService;


    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization ") String  jwt) {
        User user= userService.findUserProfileByJwt(jwt);

        return  new ResponseEntity<>(user, HttpStatus.OK);

    }


    @PostMapping("/api/users/verification/{verificationType}/send-otp")
 public ResponseEntity<String> sendVerificationOtp(
         @RequestHeader("Authorization") String  jwt,
         @PathVariable VerificationType verificationType
 ) throws MessagingException {
     User user = userService.findUserProfileByJwt(jwt);
     VerificationCode verificationCode= verificationcodeservice.getVerificationCodeByUser(user.getId());

     if(verificationCode==null) {
         verificationCode=verificationcodeservice
                 .sendVerificationCode(user, verificationType);
     }

  if(verificationType.equals(verificationType.EMAIL))
  {
      emailService.sendverificationOtpEmail(user.getEmail(), verificationCode.getOtp() );
  }

     return  new ResponseEntity<>("Verification otp send successfully ", HttpStatus.OK);

 }


    /* use to verify otp */
    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")

    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) {

        // Fetch user by JWT
        User user = userService.findUserProfileByJwt(jwt);

        // Fetch verification code
        VerificationCode verificationCode = verificationcodeservice.getVerificationCodeByUser(user.getId());

        // Check if verificationCode exists
        if (verificationCode == null || verificationCode.getOtp() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No OTP found for the user");
        }

        // Validate OTP
        if (!verificationCode.getOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid OTP");
        }

        // Determine the recipient (email or mobile)
        String sendTo = (verificationCode.getVerificationType() == VerificationType.EMAIL)
                ? verificationCode.getEmail()
                : verificationCode.getMobile();

        // Enable two-factor authentication
        User updatedUser = userService.enableTwoFactorAuthentication(
                verificationCode.getVerificationType(), sendTo, user);

        // Remove the verification code after successful verification
        verificationcodeservice.deleteVerificationCodeById(verificationCode);

        return ResponseEntity.ok(updatedUser);
    }


//send otp of pass forget 
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgetPasswordOtp(

            @RequestBody ForgetPasswordTokenRequest req
            ) throws MessagingException {

        User user = userService.findUserByEmail(req.getSendTo());
        String otp = otpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgetPasswordToken token = forgetPasswordService.findByUser(user.getId());

        if (token == null) {
            token = forgetPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
        }

        AuthResponse response = null;
        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendverificationOtpEmail(
                    user.getEmail(),
                    token.getOtp()
            );

            response = new AuthResponse();
            response.setSession(token.getId());
            response.setMessage("Password reset otp sent successfully ");


        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PatchMapping("/auth/users/reset-password/verify-otp")

    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        // Fetch user by JWT
        User user = userService.findUserProfileByJwt(jwt);

        // Fetch verification code
        ForgetPasswordToken forgetPasswordToken = forgetPasswordService.findById(id);

        boolean isverified=forgetPasswordToken.getOtp().equals(req.getOtp());

        if(isverified) {
            userService.updatePassword(forgetPasswordToken.getUser(),req.getPassword());
            ApiResponse res= new ApiResponse();
            res.setMessage("Password update successfully");

            return  new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }
        throw new Exception("wrong otp ");

    }



}
