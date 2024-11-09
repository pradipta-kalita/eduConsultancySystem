package com.pol.user_service.controller;


import com.pol.user_service.auth.dto.AuthResponseDTO;
import com.pol.user_service.auth.dto.EmailRequestDTO;
import com.pol.user_service.auth.dto.VerifyOtpDTO;
import com.pol.user_service.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/forgot-password")
public class ForgotPasswordController {

    private final EmailService emailService;

    public ForgotPasswordController(EmailService emailService) {
        this.emailService = emailService;
    }

    // first verify the email (if it exists then send OTP to that email, if not send back to register page)
    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmailAndSendOTP(@RequestBody @Valid EmailRequestDTO emailRequestDTO,
                                                        HttpServletRequest request){
        return  ResponseEntity.ok(emailService.verifyEmailAndSendOTP(emailRequestDTO));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponseDTO> verifyOTP(@RequestBody @Valid VerifyOtpDTO otpDTO,
                                                     HttpServletRequest request){
        return ResponseEntity.ok(emailService.verifyOTP(otpDTO));
    }

}