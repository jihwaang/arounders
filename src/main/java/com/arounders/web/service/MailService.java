package com.arounders.web.service;

import com.arounders.web.dto.RequestMail;
import com.arounders.web.entity.EmailAuth;
import com.arounders.web.entity.Member;
import lombok.RequiredArgsConstructor;

public interface MailService {

    public Long sendMail(RequestMail mail);

    public Long sendAuthMail(EmailAuth mail);

    public int sendNewPassword(Member memberEntity);
}
