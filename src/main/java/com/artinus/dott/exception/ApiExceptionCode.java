package com.artinus.dott.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ApiExceptionCode {

    //HTTP STATUS CODE 400
    INVALID_USER_ACCOUNT(HttpStatus.BAD_REQUEST, 4001, "아이디/비밀번호를 다시 확인해주세요."),
    NOT_APPROVED_USER_ACCOUNT(HttpStatus.BAD_REQUEST, 4002, "승인되지 않은 사용자 입니다."),
    BAD_AUTH_TOKEN(HttpStatus.BAD_REQUEST, 4003, "잘못된 인증 토큰 요청입니다."),

    //HTTP STATUS CODE 401
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED,4011, "아이디/비밀번호를 다시 확인해주세요."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 4012, "인가되지 않은 사용자 입니다."),
    AUTH_TOKEN_EXPIRE(HttpStatus.UNAUTHORIZED, 4011, "토큰 유효기간이 만료되었습니다."),
    NOT_AUTH_TOKEN_EXPIRE(HttpStatus.UNAUTHORIZED, 4012, "토큰 유효기간이 아직 만료되지 않았습니다."),
    LOGOUT_USER(HttpStatus.UNAUTHORIZED, 4013, "로그아웃된 사용자입니다."),

    //HTTP STATUS CODE 404
    NOT_FOUND_ROLE(HttpStatus.NOT_FOUND,4041, "존재하지 않은 권한입니다."),

    //HTTP STATUS CODE 409
    ALREADY_EXIST_MEMBER(HttpStatus.CONFLICT,4091, "이미 가입된 사용자입니다.");




    private HttpStatus httpStatus;
    private long code;
    private String message;
}
