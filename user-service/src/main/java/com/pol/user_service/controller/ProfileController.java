package com.pol.user_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {


    @GetMapping("/student")
    public String studentOnlyRoute(){
        return "STUDENT ONLY ROUTE";
    }


    @GetMapping("/parent")
    public String parentOnlyRoute(){
        return "PARENT ONLY ROUTE";
    }


    @GetMapping("/admin")
    public String adminOnlyRoute(){
        return "ADMIN ONLY ROUTE";
    }

    @GetMapping
    public String publicRoute(){
        return "EVERYONE CAN VISIT";
    }
}
