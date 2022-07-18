package com.artinus.dott.api.repository;

import com.artinus.dott.api.dto.type.RoleType;
import com.artinus.dott.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleType roleName);
}
