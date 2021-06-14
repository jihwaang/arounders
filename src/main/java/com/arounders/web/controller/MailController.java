package com.arounders.web.controller;

import com.arounders.web.dto.RequestMail;
import com.arounders.web.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/mail")
@RequiredArgsConstructor
@Controller
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    @ResponseBody
    public String sendMail(RequestMail mail) {
        mailService.sendMail(mail);
        return "mail sent";
    }

}
