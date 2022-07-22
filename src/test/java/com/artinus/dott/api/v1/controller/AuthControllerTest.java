package com.artinus.dott.api.v1.controller;

import com.artinus.dott.api.dto.request.SignInRequest;
import com.artinus.dott.api.dto.request.SignUpRequest;
import com.artinus.dott.api.dto.type.RoleType;
import com.artinus.dott.api.entity.Member;
import com.artinus.dott.api.repository.MemberRepository;
import com.artinus.dott.api.service.AuthService;
import com.artinus.dott.api.service.MemberService;
import com.artinus.dott.api.util.AES256Util;
import com.artinus.dott.api.v1.controller.AuthController;
import com.artinus.dott.exception.ApiException;
import com.artinus.dott.exception.ApiExceptionCode;
import com.artinus.dott.security.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    private final String authBaseUrl = "/artinus-dott/api/v1/auth";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AES256Util aes256Util;

    @Autowired
    private JwtProvider jwtProvider;

    private final String email = "whgkaud@naver.com";
    private final String password = "1234";
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 회원가입_테스트() throws Exception{
        //given
        SignUpRequest request = SignUpRequest.builder()
                .email(email)
                .password(password)
                .build();

        //when
        mockMvc.perform(
                post(authBaseUrl + "/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                ).andExpect(status().isOk());

        //then
        Member member = memberService.findByEmail(email);
        assert aes256Util.decrypt(member.getEmail()).equals(email);
        assert new BCryptPasswordEncoder().matches(password, member.getPassword());
    }

    @Test
    void 로그인_테스트() throws Exception{
        //given
        memberService.saveMemberOfUserRole(email, password);

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(authBaseUrl + "/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(SignInRequest.builder()
                                .email(email)
                                .password(password)
                                .build()))
                )
                .andExpect(status().isOk())
                .andReturn();

        //then
        String accessToken = mvcResult.getResponse().getContentAsString();
        Claims claims = jwtProvider.parseClaims(accessToken, true);

        Member member = memberService.findByEmail(email);

        assert claims.get("id", Long.class).equals(member.getId());
        assert RoleType.valueOf(claims.get("role", String.class)) == member.getRole();
    }

    @Test
    void 로그아웃_테스트() throws Exception{
        //given
        authService.signUp(SignUpRequest.builder()
                .email(email)
                .password(password)
                .build());

        String accessToken = authService.signIn(SignInRequest.builder()
                .email(email)
                .password(password)
                .build());

        //when
        mockMvc.perform(
                        post(authBaseUrl + "/signOut")
                                .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andReturn();

        //then
        mockMvc.perform(
                        post(authBaseUrl + "/signOut")
                                .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is(ApiExceptionCode.LOGOUT_USER.getCode())))
                .andExpect(jsonPath("$.message", is(ApiExceptionCode.LOGOUT_USER.getMessage())));
    }
}
