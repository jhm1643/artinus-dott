package com.artinus.dott.security;

import com.artinus.dott.api.entity.Users;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    private Users users;

    public CustomUser(Users users){
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
