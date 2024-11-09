package com.pol.user_service.service;

import com.pol.user_service.auth.dto.AuthResponseDTO;
import com.pol.user_service.auth.dto.EmailRequestDTO;
import com.pol.user_service.auth.dto.VerifyOtpDTO;
import com.pol.user_service.auth.model.ForgotPassword;
import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.repository.ForgotPasswordRepository;
import com.pol.user_service.auth.repository.UserRepository;
import com.pol.user_service.auth.service.JwtService;
import com.pol.user_service.auth.service.RefreshTokenService;
import com.pol.user_service.exception.customExceptions.InvalidOTPException;
import com.pol.user_service.exception.customExceptions.OTPExpiredException;
import com.pol.user_service.exception.customExceptions.TooManyAttemptsException;
import com.pol.user_service.exception.customExceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Service
public class EmailService {
    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final ResendService resendService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public EmailService(UserRepository userRepository, ForgotPasswordRepository forgotPasswordRepository, ResendService resendService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.resendService = resendService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Value("${jwt.forgot-expiration}")
    private long forgotPasswordExpiration;


    public String verifyEmailAndSendOTP(EmailRequestDTO emailRequestDTO) {
        User user = userRepository.findByEmail(emailRequestDTO.getEmail()).orElseThrow(()->
                new UserNotFoundException("User not found with email address : "+
                        emailRequestDTO.getEmail()));

        Integer otp = otpGenerator();
        ForgotPassword forgotPasswordObj = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date((System.currentTimeMillis()+forgotPasswordExpiration)))
                .user(user)
                .build();

        String resendId = resendService.sendForgotPasswordOTPEmail(
                user.getEmail(),
                "OTP VERIFICATION",
                user.getFirstName()+" "+user.getLastName(),
                otp.toString());
        forgotPasswordRepository.save(forgotPasswordObj);
        return "Email sent for verification";
    }


    @Transactional
    public AuthResponseDTO verifyOTP(VerifyOtpDTO verifyOtpDTO) {
        User user = userRepository.findByEmail(verifyOtpDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email address: " + verifyOtpDTO.getEmail()));

        ForgotPassword forgotPasswordObj = user.getForgotPassword();
        if (forgotPasswordObj == null) {
            throw new InvalidOTPException("The provided OTP or email is incorrect or you haven't requested for an OTP yet. Please check your details and try again.");
        }

        if (forgotPasswordObj.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPasswordObj.getId());
            throw new OTPExpiredException("OTP has expired. Please request a new one.");
        }

        if (forgotPasswordObj.getAttempts() >= 3) {
            forgotPasswordRepository.deleteById(forgotPasswordObj.getId());
            throw new TooManyAttemptsException("Too many attempts. Please request a new OTP.");
        }

        if (!forgotPasswordObj.getOtp().equals(verifyOtpDTO.getOtp())) {
            forgotPasswordObj.setAttempts(forgotPasswordObj.getAttempts() + 1);
            forgotPasswordRepository.save(forgotPasswordObj);
            throw new InvalidOTPException("Invalid OTP for email: " + verifyOtpDTO.getEmail());
        }

        user.setForgotPassword(null);
        userRepository.save(user);

        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }


    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000,999_999);
    }
}
