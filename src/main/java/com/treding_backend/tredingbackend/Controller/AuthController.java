//package com.treding_backend.tredingbackend.Controller;
//
//
//import com.treding_backend.tredingbackend.Modal.TwofactorOtp;
//import com.treding_backend.tredingbackend.Modal.User;
//
//import com.treding_backend.tredingbackend.Repository.UserRepository;
//import com.treding_backend.tredingbackend.Response.AuthResponse;
//import com.treding_backend.tredingbackend.Service.CustomUserDetailsService;
//import com.treding_backend.tredingbackend.Service.EmailService;
//import com.treding_backend.tredingbackend.Service.TwoFactorOtpService;
//import com.treding_backend.tredingbackend.Utils.otpUtils;
//import com.treding_backend.tredingbackend.config.JwtConstant;
//import com.treding_backend.tredingbackend.config.JwtProvider;
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.CachingUserDetailsService;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//
//@RestController
//@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:5173")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//@Autowired
//    private TwoFactorOtpService twoFactorOtpService;
//
//
//
//@Autowired
//private EmailService emailService;
//    // create registration
//
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
//        System.out.println("Method Executed!");
//
//        User isEmailExist= userRepository.findByEmail(user.getEmail());
//
//        if(isEmailExist!=null){
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exist");
//        }
//       //use to create  new user
//        User newUser = new User();
//        newUser.setFullName(user.getFullName());
//        newUser.setEmail(user.getEmail());
//        newUser.setPassword(user.getPassword());
//
//
//        User savedUser = userRepository.save(newUser);
//
//
//
//        Authentication auth= new UsernamePasswordAuthenticationToken(
//                user.getEmail(),
//                user.getPassword()
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//// create jwt joken
//        String jwt = JwtProvider.generateToken(auth);
//
//
//
//AuthResponse res= new AuthResponse();
//
//res.setJwt(jwt);
//res.setStatus(true);
//res.setMessage("Registration Successful");
//
//
//       return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }
//
//
//   /*-----------Login-----------------------------------------*/
//
//    @PostMapping("/signin")
//    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws MessagingException {
//
//      String username = user.getEmail();
//      String password = user.getPassword();
//
//        Authentication auth= authenticate(username,password);
//
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//// create jwt joken
//        String jwt = JwtProvider.generateToken(auth);
//
//        User authuser= userRepository.findByEmail(username);
//
//
//        /* use to send otp */
//        // ✅ NEW — Safe null check
//        if (authuser.getTwoFactorAuth() != null && Boolean.TRUE.equals(authuser.getTwoFactorAuth().getIsEnabled())) {
//
////            if(user.getTwoFactorAuth().getIsEnabled())
//        {
//                AuthResponse res= new AuthResponse();
//                res.setMessage("Two factor auth is enabled");
//                res.setTwoFactorAuthEnable(true);
//                String otp= otpUtils.generateOtp();
//
//            TwofactorOtp oldtwofactorotp = twoFactorOtpService.findByUser(authuser.getId());
//            if(oldtwofactorotp!=null)
//            {
//              twoFactorOtpService.deteleTwoFactorOtp(oldtwofactorotp);
//            }
//
//            TwofactorOtp newtwofactorotp = twoFactorOtpService.createTwofactorOtp(
//                    authuser,otp,jwt
//            );
//
//     emailService.sendverificationOtpEmail(username,otp);
//
//
//            res.setSession(newtwofactorotp.getOtp());
//
//
//            return  new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//
//        }
//
//
//        AuthResponse res= new AuthResponse();
//
//        res.setJwt(jwt);
//        res.setStatus(true);
//        res.setMessage("Login Successful");
//
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }
//
//    /*use to check the user is present or not */
//    private Authentication authenticate(String username, String password) {
//       UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//
//       if(userDetails==null){
//           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
//       }
//       if(!password.equals(userDetails.getPassword())){
//          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
//       }
//       return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//    }
//
//
//    /*use to verify the otp */
//    @PostMapping("/two-factor/otp/{otp}")
//public ResponseEntity<AuthResponse> verifysiginOtp(@PathVariable String otp,@RequestParam  String id) throws Exception {
//         TwofactorOtp twofactorOtp=twoFactorOtpService.findById(id);
//
//         if(twoFactorOtpService.verifyTwofactorOtp(twofactorOtp,otp)) {
//             AuthResponse res = new AuthResponse();
//             res.setMessage("Two factor authentication verified");
//             res.setTwoFactorAuthEnable(true);
//             res.setJwt(twofactorOtp.getJwt());
//
//             return  new ResponseEntity<>(res, HttpStatus.OK);
//
//
//
//         }
//         throw  new Exception("Invalid Otp");
//
//}
//
//
//}
//
//
//


package com.treding_backend.tredingbackend.Controller;

import com.treding_backend.tredingbackend.Modal.Logininfo;
import com.treding_backend.tredingbackend.Modal.TwoFactorAuth;
import com.treding_backend.tredingbackend.Modal.TwofactorOtp;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Repository.UserRepository;
import com.treding_backend.tredingbackend.Response.AuthResponse;
import com.treding_backend.tredingbackend.Service.CustomUserDetailsService;
import com.treding_backend.tredingbackend.Service.EmailService;
import com.treding_backend.tredingbackend.Service.TwoFactorOtpService;
import com.treding_backend.tredingbackend.Utils.otpUtils;
import com.treding_backend.tredingbackend.config.JwtProvider;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    // ------------------ Registration ------------------ //
//
//    @PostMapping("/signup")
//    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
//        System.out.println("Method Executed!");
//
//        if (userRepository.findByEmail(user.getEmail()) != null) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exists");
//        }
//
//        User newUser = new User();
//        newUser.setFullName(user.getFullName());
//        newUser.setEmail(user.getEmail());
//        newUser.setPassword(user.getPassword());
//
//        userRepository.save(newUser);
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(
//                user.getEmail(),
//                user.getPassword()
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        String jwt = JwtProvider.generateToken(auth);
//
//        AuthResponse res = new AuthResponse();
//        res.setJwt(jwt);
//        res.setStatus(true);
//        res.setMessage("Registration Successful");
//
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        System.out.println("Method Executed!");

        if (user.getEmail() == null || user.getEmail().isBlank() ||
                user.getPassword() == null || user.getPassword().isBlank() ||
                user.getFullName() == null || user.getFullName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email, Password, and Full Name are required");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exists");
        }

        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        // Optional: Set default TwoFactorAuth values
        if (newUser.getTwoFactorAuth() == null) {
            TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
            twoFactorAuth.setIsEnabled(false); // default
            newUser.setTwoFactorAuth(twoFactorAuth);
        }

        userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Registration Successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    // ------------------ Login ------------------ //

//    @PostMapping("/signin")
//    public ResponseEntity<?> login(@RequestBody User user) throws MessagingException {
//        String username = user.getEmail();
//        String password = user.getPassword();
//
//        Authentication auth = authenticate(username, password);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        String jwt = JwtProvider.generateToken(auth);
//        User authUser = userRepository.findByEmail(username);
//
//        if (authUser.getTwoFactorAuth() != null &&
//                Boolean.TRUE.equals(authUser.getTwoFactorAuth().getIsEnabled())) {
//
//            // Clean old OTP if exists
//            TwofactorOtp oldOtp = twoFactorOtpService.findByUser(authUser.getId());
//            if (oldOtp != null) {
//                twoFactorOtpService.deteleTwoFactorOtp(oldOtp);
//            }
//
//            String otp = otpUtils.generateOtp();
//            TwofactorOtp newOtp = twoFactorOtpService.createTwofactorOtp(authUser, otp, jwt);
//            emailService.sendverificationOtpEmail(username, otp);
//
//            AuthResponse res = new AuthResponse();
//            res.setMessage("Two-factor auth is enabled");
//            res.setTwoFactorAuthEnable(true);
//            res.setSession(newOtp.getOtp());
//
//            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//        }
//
//        AuthResponse res = new AuthResponse();
//        res.setJwt(jwt);
//        res.setStatus(true);
//        res.setMessage("Login Successful");
//
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody Logininfo logininfo) throws MessagingException {
        String username = logininfo.getEmail();
        String password = logininfo.getPassword();

        Authentication auth = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);
        User authUser = userRepository.findByEmail(username);

        if (authUser.getTwoFactorAuth() != null &&
                Boolean.TRUE.equals(authUser.getTwoFactorAuth().getIsEnabled())) {

            TwofactorOtp oldOtp = twoFactorOtpService.findByUser(authUser.getId());
            if (oldOtp != null) {
                twoFactorOtpService.deteleTwoFactorOtp(oldOtp);
            }

            String otp = otpUtils.generateOtp();
            TwofactorOtp newOtp = twoFactorOtpService.createTwofactorOtp(authUser, otp, jwt);
            emailService.sendverificationOtpEmail(username, otp);

            AuthResponse res = new AuthResponse();
            res.setMessage("Two-factor auth is enabled");
            res.setTwoFactorAuthEnable(true);
            res.setSession(newOtp.getOtp());

            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login Successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    // ------------------ Authenticate ------------------ //

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null || !password.equals(userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    // ------------------ Verify OTP ------------------ //

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigninOtp(@PathVariable String otp, @RequestParam String id) throws Exception {
        TwofactorOtp twofactorOtp = twoFactorOtpService.findById(id);

        if (twoFactorOtpService.verifyTwofactorOtp(twofactorOtp, otp)) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two-factor authentication verified");
            res.setTwoFactorAuthEnable(true);
            res.setJwt(twofactorOtp.getJwt());

            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        throw new Exception("Invalid OTP");
    }
}

