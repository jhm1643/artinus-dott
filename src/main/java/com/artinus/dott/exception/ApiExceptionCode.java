package com.artinus.dott.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ApiExceptionCode {

    //HTTP STATUS CODE 401
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED,4011, "존재하지 않은 사용자 정보 입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 4012, "인가되지 않은 사용자 입니다."),

    //HTTP STATUS CODE 404
    NOT_FOUND_ROLE(HttpStatus.NOT_FOUND,4041, "존재하지 않은 권한입니다.");




    private HttpStatus httpStatus;
    private int code;
    private String message;
}
