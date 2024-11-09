package com.pol.user_service.auth.config;

import com.pol.user_service.auth.model.UserRole;
import com.pol.user_service.auth.model.UserRoleEnum;
import com.pol.user_service.auth.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(UserRoleRepository roleRepository) {
        return args -> {
            List<String> roles = Arrays.stream(UserRoleEnum.values())
                    .map(Enum::name)
                    .toList();
            for (String roleName : roles) {
                if (roleRepository.findByRoleName(roleName).isEmpty()) {
                    roleRepository.save(UserRole
                            .builder()
                                    .roleName(roleName)
                            .build());
                }
            }
        };
    }
}
