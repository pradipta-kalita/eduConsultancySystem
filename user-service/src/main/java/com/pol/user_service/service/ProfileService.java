package com.pol.user_service.service;

import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.repository.UserRepository;
import com.pol.user_service.auth.service.AuthService;
import com.pol.user_service.auth.service.JwtService;
import com.pol.user_service.dto.user.UserDetailsDTO;
import com.pol.user_service.exception.customExceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    public ProfileService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public UserDetailsDTO getUserDetails(String authHeader){
        if(authHeader==null ||!authHeader.startsWith("Bearer ")){
            throw new RuntimeException("Authentication token is required");
        }
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);
        return userRepository.getUserDetailsByEmail(email).orElseThrow(()->new UserNotFoundException("User not found with email : "+email));
    }
}
