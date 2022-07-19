package com.artinus.dott.api.service;

import com.artinus.dott.api.dto.request.SignInRequest;
import com.artinus.dott.api.dto.request.UserRegistRequest;
import com.artinus.dott.api.dto.type.RoleType;
import com.artinus.dott.api.entity.Role;
import com.artinus.dott.api.entity.Users;
import com.artinus.dott.api.repository.RoleRepository;
import com.artinus.dott.api.repository.UsersRepository;
import com.artinus.dott.api.util.AES256Util;
import com.artinus.dott.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AES256Util aes256Util;

    public void signUp(UserRegistRequest request){
        Role role = roleRepository.findByRoleName(RoleType.USER_ROLE)
                .orElseThrow(NoSuchElementException::new);

        usersRepository.save(Users.builder()
                .email(aes256Util.encrypt(request.getEmail()))
                .password(passwordEncoder.encode(request.getPassword()))
                .updateDt(LocalDateTime.now())
                .roles(Collections.singletonList(role))
                .build());
    }

    public void signIn(SignInRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    }
}
