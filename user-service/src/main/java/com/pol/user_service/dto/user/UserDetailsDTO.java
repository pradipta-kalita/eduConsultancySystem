package com.pol.user_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDetailsDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

}
