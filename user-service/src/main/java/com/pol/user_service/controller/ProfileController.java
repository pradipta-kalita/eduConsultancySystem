package com.pol.user_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/student")
    public String studentOnlyRoute(){
        return "STUDENT ONLY ROUTE";
    }

    @PreAuthorize("hasAuthority('PARENT')")
    @GetMapping("/parent")
    public String parentOnlyRoute(){
        return "PARENT ONLY ROUTE";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String adminOnlyRoute(){
        return "ADMIN ONLY ROUTE";
    }

    @GetMapping
    public String publicRoute(){
        return "EVERYONE CAN VISIT";
    }
}
