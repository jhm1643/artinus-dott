package com.artinus.dott.api.v1.controller;

import com.artinus.dott.api.dto.request.SignUpRequest;
import com.artinus.dott.api.entity.Member;
import com.artinus.dott.api.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "사용자 API")
@RestController
@RequestMapping("/artinus-dott/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "사용자 조회")
    @GetMapping
    public ResponseEntity<Member> findByEmail(
            @ApiParam(name = "email", required = true)
            @RequestParam(value = "email") String email
    ){
        return ResponseEntity.ok(memberService.findByEmail(email));
    }
}
