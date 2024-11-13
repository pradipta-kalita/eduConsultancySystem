package com.pol.user_service.controller;

import com.pol.user_service.auth.dto.AuthResponseDTO;
import com.pol.user_service.auth.dto.LoginRequestDTO;
import com.pol.user_service.auth.dto.RefreshTokenRequestDTO;
import com.pol.user_service.auth.dto.RegisterRequestDTO;
import com.pol.user_service.auth.model.RefreshToken;
import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.service.AuthService;
import com.pol.user_service.auth.service.JwtService;
import com.pol.user_service.auth.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO,
                                                    HttpServletRequest request
                                                   ){
        return ResponseEntity.ok(authService.register(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO,
                                                 HttpServletRequest request
                                                 ){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO refreshTokenRequestDTO,
                                                        HttpServletRequest request){
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequestDTO.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
}