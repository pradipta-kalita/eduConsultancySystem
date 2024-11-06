package com.pol.user_service.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,length = 500)
    @NotBlank(message = "Please enter refresh token value.")
    private String refreshToken;

    @Column(nullable = false)
    @NotNull
    private Instant expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User user;
}