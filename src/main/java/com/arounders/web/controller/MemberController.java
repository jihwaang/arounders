package com.arounders.web.controller;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Role;
import com.arounders.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public @ResponseBody String doSignUp(MemberDTO requestMember) {
      log.info("memberDTO : {}", requestMember);
      Long id = memberService.signup(requestMember);
      return "";
    }
}
