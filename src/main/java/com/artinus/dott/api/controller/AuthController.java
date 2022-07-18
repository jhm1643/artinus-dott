package com.artinus.dott.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/artinus-dott/api/v1/auth")
public class AuthController {

    @PostMapping("/login")
    public void login(HttpServletResponse response){

    }
}
