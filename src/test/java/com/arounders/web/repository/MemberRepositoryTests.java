package com.arounders.web.repository;

import com.arounders.web.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void getMemberTest(){

        Member member = memberRepository.getMember(1L);

        Assertions.assertThat(member.getId()).isEqualTo(1L);
    }

    @Test
    public void insertTest(){

        String email = "admin@gorany.com";
        String password = "12345";
        String nickname = "admin";
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

        int result = memberRepository.insert(member);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void updateTest(){

        Long id = 10L;
        String email = "test@naver.com";
        String password = "54321";
        String nickname = "test123";
        String phone = "010-1010-2020";
        String addr = "서울시 양천구 영등포동 영등포로 102";
        String addrDtl = "OO빌라 201호";

        Member member = Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .phone(phone)
                .addr(addr)
                .addrDtl(addrDtl)
                .build();

        int result = memberRepository.update(member);
        System.out.println("member = " + member);
        Assertions.assertThat(member.getNickname()).isEqualTo("test123");
    }

    @Test
    public void deleteTest(){

        Long id = 11L;

        memberRepository.delete(id);
        Member member = memberRepository.getMember(id);
        System.out.println(member.getDroppedAt());
    }

    @Test
    public void getMembersTest(){
        List<Member> members = memberRepository.getMembers();

        members.forEach(System.out::println);
    }
}
