package com.artinus.dott.security.jwt;

import com.artinus.dott.api.dto.response.AuthTokenDto;
import com.artinus.dott.api.entity.Users;
import com.artinus.dott.security.CustomUser;
import com.artinus.dott.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // 7일
    private long tokenValidTime = 1000L * 60 * 60;

    private final Key key;
    public JwtProvider(@Value("${jwt.secret}") String secretKey){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        return Jwts.builder()
            .claim("role", authorities)
            .claim("email", authentication.getPrincipal())
            .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key)
            .compact();
    }

    public Authentication getAuthentication(Claims claims) {
        String authority = claims.get("role", String.class);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(authority));
        Users users = Users.builder()
                .email(claims.get("email", String.class))
                .password("")
                .build();

        return new UsernamePasswordAuthenticationToken(new CustomUser(users), "", authorities);
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

