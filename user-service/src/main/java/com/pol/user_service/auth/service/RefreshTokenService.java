package com.pol.user_service.auth.service;

import com.pol.user_service.auth.model.RefreshToken;
import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.repository.RefreshTokenRepository;
import com.pol.user_service.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    public RefreshToken createRefreshToken(String username){
        User user=userRepository.
                findByEmail(username).
                orElseThrow(()->
                        new UsernameNotFoundException("User not found with email : "+username));
        RefreshToken refreshToken = user.getRefreshToken();
        if(refreshToken==null){
            refreshToken = RefreshToken
                    .builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenExpiration))
                    .user(user)
                    .build();

            refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken){
        RefreshToken existingRefreshToken = refreshTokenRepository
                .findByRefreshToken(refreshToken)
                .orElseThrow(()->new RuntimeException("Refresh token not found"));

        if(existingRefreshToken.getExpirationTime().compareTo(Instant.now())<0){
            refreshTokenRepository.deleteById(existingRefreshToken.getId());
            throw new RuntimeException("Refresh Token expired. Please login again.");
        }
        return existingRefreshToken;
    }
}
