package com.pol.user_service.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {
    @Email(message = "Email address is not valid.")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8,message = "Password should be at least 8 characters long.")
    private String password;
}
