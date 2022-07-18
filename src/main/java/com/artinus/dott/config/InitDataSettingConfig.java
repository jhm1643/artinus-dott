package com.artinus.dott.config;

import com.artinus.dott.api.dto.type.RoleType;
import com.artinus.dott.api.entity.Role;
import com.artinus.dott.api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class InitDataSettingConfig {

    private final RoleRepository roleRepository;

    @Bean
    public void setRoleData(){
        List<Role> roles = new LinkedList<>();
        roles.add(Role.builder()
                .roleName(RoleType.USER_ROLE).build());
        roles.add(Role.builder()
                .roleName(RoleType.ADMIN_ROLE).build());
        roleRepository.saveAll(roles);
    }
}
