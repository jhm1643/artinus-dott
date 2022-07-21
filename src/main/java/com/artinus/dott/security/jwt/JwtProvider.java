package com.artinus.dott.security.jwt;

import com.artinus.dott.api.dto.type.RoleType;
import com.artinus.dott.api.entity.Member;
import com.artinus.dott.security.CustomUser;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // 30분
    private long tokenValidTime = 1000L * 60 * 60;

    private final Key key;
    public JwtProvider(@Value("${jwt.secret}") String secretKey){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Authentication authentication){
        Member member = ((CustomUser) authentication.getPrincipal()).getMember();
        final long now = new Date().getTime();
        return Jwts.builder()
            .claim("role", member.getRole().name())
            .claim("id", member.getId())
            .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key)
            .compact();
    }

    public Authentication getAuthentication(Claims claims) {
        Member member = Member.builder()
                .id(claims.get("id", Long.class))
                .password("")
                .role(RoleType.valueOf(claims.get("role", String.class)))
                .build();

        String role = claims.get("role", String.class);
        return new UsernamePasswordAuthenticationToken(new CustomUser(member), "", Collections.singletonList(new SimpleGrantedAuthority(role)));
    }

    public Claims parseClaims(String accessToken, Boolean isExpiredCheck) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            if(isExpiredCheck)
                throw e;
            else
                return e.getClaims();
        }
    }

    public Boolean isExpireToken(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String getExpireToken(String accessToken){
        return Jwts.builder().setClaims(parseClaims(accessToken, false).setExpiration(new Date())).compact();
    }
}

