package com.artinus.dott.api.v1.controller;

import com.artinus.dott.api.dto.request.UserRegistRequest;
import com.artinus.dott.api.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "인증 API")
@RestController
@RequestMapping("/artinus-dott/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signUp")
    public void SignUp(
            @RequestBody @Valid UserRegistRequest request
    ){
        authService.signUp(request);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/signIn")
    public void signIn(
            @RequestBody @Valid
            HttpServletResponse response){

    }
}
