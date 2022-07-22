package com.artinus.dott.api.service;

import com.artinus.dott.api.dto.type.RoleType;
import com.artinus.dott.api.entity.Member;
import com.artinus.dott.api.repository.MemberRepository;
import com.artinus.dott.api.util.AES256Util;
import com.artinus.dott.exception.ApiException;
import com.artinus.dott.exception.ApiExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AES256Util aes256Util;

    public Member findByEmail(String email){
        return memberRepository.findByEmail(aes256Util.encrypt(email))
                .orElseThrow(() -> new ApiException(ApiExceptionCode.NOT_FOUND_USER));
    }

    public void saveMemberOfUserRole(String email, String password){
        memberRepository.save(
                Member.builder()
                        .email(aes256Util.encrypt(email))
                        .password(new BCryptPasswordEncoder().encode(password))
                        .role(RoleType.USER_ROLE)
                        .build()
        );
    }
}
