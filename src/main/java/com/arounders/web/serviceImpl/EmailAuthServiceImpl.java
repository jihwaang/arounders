package com.arounders.web.serviceImpl;

import com.arounders.web.entity.EmailAuth;
import com.arounders.web.repository.EmailAuthRepository;
import com.arounders.web.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailAuthServiceImpl implements EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;

    @Override
    public EmailAuth getAuthEmail(EmailAuth email) {
        return emailAuthRepository.findEmailByEmail(email);
    }

    @Override
    public Long requestAuth(EmailAuth email) {
        return emailAuthRepository.insert(email);
    }

    @Override
    public Long confirmAuth(EmailAuth email) {
        return emailAuthRepository.update(email);
    }

}
