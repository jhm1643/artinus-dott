package com.artinus.dott.exception;

public class ApiException extends RuntimeException{

    private ApiExceptionCode apiExceptionCode;

    public ApiException(ApiExceptionCode apiExceptionCode){
        this.apiExceptionCode = apiExceptionCode;
    }
}
