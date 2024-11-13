package com.pol.user_service.auth.service;


import com.pol.user_service.auth.dto.AuthResponseDTO;
import com.pol.user_service.auth.dto.LoginRequestDTO;
import com.pol.user_service.auth.dto.RegisterRequestDTO;
import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.model.UserRole;
import com.pol.user_service.auth.repository.UserRepository;
import com.pol.user_service.auth.repository.UserRoleRepository;
import com.pol.user_service.constants.UserRoleConstants;
import com.pol.user_service.exception.customExceptions.DatabaseAccessException;
import com.pol.user_service.exception.customExceptions.UserAlreadyExists;
import com.pol.user_service.service.ResendService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRoleService userRoleService;
    private final ResendService resendService;

    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO){
        if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new RuntimeException("User with email id "+registerRequestDTO.getEmail()+" already exists");
        }
        if(userRepository.existsByUsername(registerRequestDTO.getUsername())){
            throw new RuntimeException("User with username "+registerRequestDTO.getUsername()+" already exists");
        }
        UserRole defaultRole = userRoleService.getDefaultRoleUser();
        var user = User.builder()
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .roles(Collections.singleton(defaultRole))
                .build();
        try {
            User savedUser = userRepository.save(user);
            resendService.sendWelcomeEmail(savedUser.getEmail(),"WELCOME "+savedUser.getFirstName()+" "+savedUser.getLastName(),registerRequestDTO.getUsername());
            var accessToken = jwtService.generateToken(savedUser);
            var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());
            return AuthResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExists("A user with this email already exists.");
        } catch (ConstraintViolationException e) {
            // Handle validation constraint failure from db
            throw new RuntimeException("User data is invalid.");
        } catch (DataAccessException e) {
            // Handle general database access errors
            throw new DatabaseAccessException("Unable to save user due to database error.");
        }
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequestDTO
                        .getEmail())
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with email : " + loginRequestDTO.getEmail()));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequestDTO.getEmail());

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
