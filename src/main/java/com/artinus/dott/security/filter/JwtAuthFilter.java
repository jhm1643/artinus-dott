package com.artinus.dott.security.filter;

import com.artinus.dott.api.service.AuthService;
import com.artinus.dott.exception.ApiException;
import com.artinus.dott.exception.ApiExceptionCode;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TYPE = "Bearer ";
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        try{
            if(!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(BEARER_TYPE))
                throw new ApiException(ApiExceptionCode.UNAUTHORIZED);

            String token = bearerToken.substring(BEARER_TYPE.length());
            authService.validationAccessToken(token);
        }catch (Exception e) {
            authExceptionHandler.exceptionHandler(response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
