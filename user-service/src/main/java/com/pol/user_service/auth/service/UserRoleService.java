package com.pol.user_service.auth.service;

import com.pol.user_service.auth.model.UserRole;
import com.pol.user_service.auth.model.UserRoleEnum;
import com.pol.user_service.auth.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole getDefaultRoleUser() {
        return userRoleRepository.findByRoleName(String.valueOf(UserRoleEnum.STUDENT))
                .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found"));
    }
}
