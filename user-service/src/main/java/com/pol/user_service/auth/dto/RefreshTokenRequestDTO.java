package com.pol.user_service.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefreshTokenRequestDTO {
    @NotBlank(message = "Refresh token is required.")
    private String refreshToken;
}
