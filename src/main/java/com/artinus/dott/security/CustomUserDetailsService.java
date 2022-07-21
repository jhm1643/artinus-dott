package com.artinus.dott.security;

import com.artinus.dott.exception.ApiExceptionCode;
import com.artinus.dott.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return new CustomUser(memberRepository.findById(Long.valueOf(id)).orElseThrow(()->new UsernameNotFoundException(ApiExceptionCode.NOT_FOUND_USER.getMessage())));
    }
}
