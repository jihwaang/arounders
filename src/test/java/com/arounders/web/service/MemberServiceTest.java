package com.arounders.web.service;

import com.arounders.web.entity.Member;
import com.arounders.web.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService service;

    @Test
    void getMember() {
    }

    @Test
    void getMembers() {
    }

    @Test
    @Transactional
    void signup() {

        String email = "admin@gorany.com";
        String password = "12345";
        String nickname = "q1w2w2e3";
        String phone = "010-1010-2020";
        String addr = "서울시 강북구 삼양동 귀로 102";
        String addrDtl = "OO빌라 201호";

        Member member = Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phone(phone)
                .addr(addr)
                .addrDtl(addrDtl)
                .build();

        Long id = service.signup(member);

        Assertions.assertThat(id).isNotEqualTo(null);
    }

    @Test
    void update() {
    }

    @Test
    void dropOut() {
    }
}