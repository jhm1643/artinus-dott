package com.artinus.dott.api.service;

import com.artinus.dott.api.dto.request.SignInRequest;
import com.artinus.dott.api.dto.request.SignUpRequest;
import com.artinus.dott.api.dto.type.RoleType;
import com.artinus.dott.api.entity.BlackList;
import com.artinus.dott.api.entity.Member;
import com.artinus.dott.api.repository.BlackListRepository;
import com.artinus.dott.api.repository.MemberRepository;
import com.artinus.dott.api.util.AES256Util;
import com.artinus.dott.exception.ApiException;
import com.artinus.dott.exception.ApiExceptionCode;
import com.artinus.dott.security.CustomUser;
import com.artinus.dott.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final AES256Util aes256Util;

    private final MemberRepository memberRepository;
    private final BlackListRepository blackListRepository;

    public void signUp(SignUpRequest request){
        boolean existMember = memberRepository.findByEmail(aes256Util.encrypt(request.getEmail())).isPresent();
        if(existMember)
            throw new ApiException(ApiExceptionCode.ALREADY_EXIST_MEMBER);

        memberRepository.save(Member.builder()
                .email(aes256Util.encrypt(request.getEmail()))
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .updateDt(LocalDateTime.now())
                .role(RoleType.USER_ROLE)
                .build());
    }

    public String signIn(SignInRequest request){
        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(new UsernamePasswordAuthenticationToken(aes256Util.encrypt(email), password));

        Member member = ((CustomUser) authentication.getPrincipal()).getMember();
        String jwtToken = jwtProvider.generateToken(member);

        deleteDlackList(member.getId());
        return jwtToken;
    }

    public void singOut(Long memberId) {
        blackListRepository.save(BlackList.builder()
                .memberId(memberId)
                .build());
    }

    public void validateAccessToken(String accessToken) {
        Claims claims = jwtProvider.parseClaims(accessToken, true);
        checkBlackList(claims.get("id", Long.class));
        SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(claims));
    }

    private void deleteDlackList(Long memberId){
        blackListRepository.findById(memberId)
                .ifPresent(blackList -> blackListRepository.delete(blackList));
    }

    private void checkBlackList(Long memberId){
        if(blackListRepository.findById(memberId).isPresent())
            throw new ApiException(ApiExceptionCode.LOGOUT_USER);
    }
}
