package com.artinus.dott.security.filter.exception;

import com.artinus.dott.exception.ApiException;
import com.artinus.dott.exception.ApiExceptionCode;
import com.artinus.dott.exception.ApiExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class AuthExceptionHandler {

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json;charset=UTF-8";
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void exceptionHandler(HttpServletResponse response, Exception e){
        ApiExceptionCode apiExceptionCode;

        if (e.getClass().equals(ExpiredJwtException.class))
            apiExceptionCode = ApiExceptionCode.AUTH_TOKEN_EXPIRE;
        else if(e.getClass().isAssignableFrom(AuthenticationException.class))
            apiExceptionCode = ApiExceptionCode.INVALID_USER_ACCOUNT;
        else if(e.getClass().equals(AccessDeniedException.class))
            apiExceptionCode = ApiExceptionCode.NOT_APPROVED_USER_ACCOUNT;
        else if(e.getClass().equals(ApiException.class))
            apiExceptionCode = ((ApiException) e).getApiExceptionCode();
        else
            apiExceptionCode = ApiExceptionCode.BAD_AUTH_TOKEN;

        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        response.getWriter().write(objectMapper.writeValueAsString(ApiExceptionHandler.ErrorResponse.builder()
                .code(apiExceptionCode.getCode())
                .message(apiExceptionCode.getMessage())
                .build()));
        response.setStatus(apiExceptionCode.getHttpStatus().value());
    }
}
