package com.pol.user_service.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyOtpDTO {
    @NotBlank(message = "Please provide email address.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotNull(message = "Please provide OTP send to your registered email address.")
    private Integer otp;
}