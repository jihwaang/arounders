package com.arounders.web.controller;

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
    public EmailAuth getAuthEmail(@RequestBody EmailAuth email) {
        EmailAuth emailAuth = emailAuthService.getAuthEmail(email);
        return emailAuth;
    }

    @PostMapping("/auth")
    public Long requestAuth(@RequestBody EmailAuth email) {
        log.info("email: {}", email);
        Long id = emailAuthService.requestAuth(email);
        return id;
    }

    @PutMapping("/auth")
    public Long confirmAuth(@RequestBody EmailAuth email) {
        log.info("email: {}", email);
        Long id = emailAuthService.confirmAuth(email);
        return id;
    }
}
