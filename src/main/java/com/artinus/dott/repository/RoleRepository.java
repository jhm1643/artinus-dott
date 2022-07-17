package com.artinus.dott.repository;

import com.artinus.dott.dto.type.RoleType;
import com.artinus.dott.entity.Role;
import com.artinus.dott.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleType roleName);
}
