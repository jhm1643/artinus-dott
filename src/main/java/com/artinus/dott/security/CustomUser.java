package com.artinus.dott.security;

import com.artinus.dott.api.entity.Member;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class CustomUser extends User {

    private Member member;

    public CustomUser(Member member){
        super(
                String.valueOf(member.getId()),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()))
        );
        this.member = member;
    }
}
