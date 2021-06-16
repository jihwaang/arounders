package com.arounders.web.service;

import com.arounders.web.dto.RequestMail;
import com.arounders.web.entity.EmailAuth;
import lombok.RequiredArgsConstructor;

public interface MailService {

    public Long sendMail(RequestMail mail);

    public Long sendAuthMail(EmailAuth mail);

}
