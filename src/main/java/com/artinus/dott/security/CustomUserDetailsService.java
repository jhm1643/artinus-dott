package com.artinus.dott.security;

import com.artinus.dott.exception.ApiExceptionCode;
import com.artinus.dott.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetails(usersRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(ApiExceptionCode.NOT_FOUND_USER.getMessage())));
    }
}
