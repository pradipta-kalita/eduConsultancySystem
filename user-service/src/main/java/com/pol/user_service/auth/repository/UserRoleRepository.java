package com.pol.user_service.auth.repository;

import com.pol.user_service.auth.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    Optional<UserRole> findByRoleName(String roleName);
}
