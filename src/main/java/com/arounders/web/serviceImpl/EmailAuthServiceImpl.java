package com.arounders.web.serviceImpl;

import com.arounders.web.entity.EmailAuth;
import com.arounders.web.repository.EmailAuthRepository;
import com.arounders.web.service.EmailAuthService;
import com.arounders.web.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmailAuthServiceImpl implements EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;
    private final MailService mailService;

    @Override
    public EmailAuth getAuthEmail(EmailAuth email) {
        return emailAuthRepository.findEmailByEmail(email);
    }

    @Override
    public String requestAuth(EmailAuth email) {
        email.setConfirmKey(UUID.randomUUID().toString());
        emailAuthRepository.insert(email);
        mailService.sendAuthMail(email);
        return emailAuthRepository.findConfirmKeyById(email);
    }

    @Override
    public EmailAuth findByConfirmKey(EmailAuth email) {
        return emailAuthRepository.findByConfirmKey(email);
    }

    @Override
    public Long confirmAuth(EmailAuth email) {
        EmailAuth emailWithConfirmKey = findByConfirmKey(email);
        if (emailWithConfirmKey == null) return -1L;
        return emailAuthRepository.update(emailWithConfirmKey);
    }

    @Override
    public Long confirmAuthByKey(String confirmKey) {
        return emailAuthRepository.updateByKey(confirmKey);
    }

    @Override
    public Long checkDuplicatedConfirm(String confirmKey) {
        return emailAuthRepository.getCountConfirmed(confirmKey);
    }

}
