package com.pol.user_service.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequestDTO {
    @NotBlank(message = "Please provide a email address.")
    @Email(message = "Please provide a valid email address.")
    private String email;
}
