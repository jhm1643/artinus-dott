package com.artinus.dott.api.controller;

import com.artinus.dott.api.dto.request.UserRegistRequest;
import com.artinus.dott.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "사용자 API")
@RestController
@RequestMapping("/artinus-dott/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "사용자 등록")
    @PostMapping
    public void UserRegist(
            @RequestBody @Valid UserRegistRequest request
    ){
        userService.UserRegist(request);
    }
}
