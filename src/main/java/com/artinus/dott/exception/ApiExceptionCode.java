package com.artinus.dott.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ApiExceptionCode {

    NOT_FOUND_ROLE(HttpStatus.NOT_FOUND,4041, "존재하지 않은 권한입니다."),
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED,4011, "존재하지 않은 사용자 정보 입니다.");


    private HttpStatus httpStatus;
    private int code;
    private String message;
}
