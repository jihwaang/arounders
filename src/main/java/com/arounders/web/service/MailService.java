package com.arounders.web.service;

import com.arounders.web.dto.RequestMail;
import lombok.RequiredArgsConstructor;

public interface MailService {

    public void sendMail(RequestMail mail);

}
