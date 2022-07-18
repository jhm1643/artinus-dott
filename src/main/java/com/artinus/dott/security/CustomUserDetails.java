package com.artinus.dott.security;

import com.artinus.dott.api.entity.Users;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

    private Users users;

    public CustomUserDetails(Users users){
        super(
                users.getEmail(),
                users.getPassword(),
                AuthorityUtils.createAuthorityList(users.getRoles().stream()
                        .map(role -> role.getRoleName())
                        .toArray(String[]::new))
        );

        this.users = users;
    }
}
