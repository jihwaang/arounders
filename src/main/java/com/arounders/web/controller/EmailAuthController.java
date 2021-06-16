package com.arounders.web.controller;

import com.arounders.web.dto.EmailAuthDTO;
import com.arounders.web.entity.EmailAuth;
import com.arounders.web.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/emailAuth")
@RequiredArgsConstructor
@RestController
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @GetMapping("/auth/{email}")
    public EmailAuth getAuthEmail(@PathVariable("email") String email) {
        EmailAuth emailAuth = emailAuthService.getAuthEmail(EmailAuth.builder().email(email).build());
        return emailAuth;
    }

    @PostMapping("/confirmCheck")
    public EmailAuth checkConfirmations(@RequestBody EmailAuth emailAuth) {
        EmailAuth responseEmailAuth = emailAuthService.findByConfirmKey(emailAuth);
        return responseEmailAuth;
    }

    @GetMapping("/confirm")
    public String confirmAuthEmail(String confirmKey) {
        log.info("confirmKey: {}", confirmKey);
        Long count = emailAuthService.checkDuplicatedConfirm(confirmKey);
        if (count > 0) return "이미 인증이 완료되었습니다.";
        Long result = emailAuthService.confirmAuthByKey(confirmKey);
        return result == 1 ? "인증에 성공하였습니다. 회원가입을 마무리 해주세요." : "인증에 실패하였습니다. 다시 시도해주세요.";
    }

    @PostMapping(value = "/auth")
    public EmailAuthDTO requestAuth(@RequestBody EmailAuth email) {
        log.info("email: {}", email);
        String confirmKey = emailAuthService.requestAuth(email);
        return EmailAuthDTO.builder().confirmKey(confirmKey).build();
    }

    @PutMapping("/auth")
    public Long confirmAuth(@RequestBody EmailAuth email) {
        log.info("email: {}", email);
        Long id = emailAuthService.confirmAuth(email);
        return id;
    }
}
