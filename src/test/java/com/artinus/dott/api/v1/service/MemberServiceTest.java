package com.artinus.dott.api.v1.service;

import com.artinus.dott.api.repository.MemberRepository;
import com.artinus.dott.api.service.MemberService;
import com.artinus.dott.api.util.AES256Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AES256Util aes256Util;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 사용자_추가_조회(){
        String email = "whgkaud@naver.com";
        String password = "1234";
        memberService.saveMemberOfUserRole(email, password);
        memberRepository.findByEmail(aes256Util.encrypt(email));
//        memberService.findByEmail("whgkaud@naver.com");
    }
}
