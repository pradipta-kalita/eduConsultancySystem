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
public class RegisterRequestDTO {

    @NotBlank(message = "First name is required.")
    @Size(max = 30, message = "First name can not longer than 30 letters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 30, message = "Last name can not longer than 30 letters.")
    private String LastName;

    @Email(message = "Email address is not valid.")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required.")
    @Size(max = 30,message = "Public user name can not hold more than 30 characters.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8,message = "Password should be at least 8 characters long.")
    private String password;
}
