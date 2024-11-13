package com.pol.user_service.controller;

import com.pol.user_service.auth.model.User;
import com.pol.user_service.dto.user.UserDetailsDTO;
import com.pol.user_service.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<UserDetailsDTO> getUserDetails(
            @RequestHeader("Authorization") String authHeader
    ){
        return ResponseEntity.ok(profileService.getUserDetails(authHeader));
    }

}
