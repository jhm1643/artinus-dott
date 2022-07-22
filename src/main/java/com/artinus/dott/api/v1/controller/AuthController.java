package com.artinus.dott.api.v1.controller;

import com.artinus.dott.annotation.AuthMember;
import com.artinus.dott.api.dto.request.SignInRequest;
import com.artinus.dott.api.dto.request.SignUpRequest;
import com.artinus.dott.api.entity.Member;
import com.artinus.dott.api.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            @RequestBody @Valid SignUpRequest request
    ){
        authService.signUp(request);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(
            @RequestBody @Valid SignInRequest request
    ){
       return ResponseEntity.ok(authService.signIn(request));
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping("/signOut")
    public void signOut(
            @AuthMember Member member
    ){
        authService.signOut(member.getId());
    }
}
