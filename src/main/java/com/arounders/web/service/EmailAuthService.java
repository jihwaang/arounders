package com.arounders.web.service;

import com.arounders.web.entity.EmailAuth;

public interface EmailAuthService {
    EmailAuth getAuthEmail(EmailAuth email);

    Long requestAuth(EmailAuth email);

    Long confirmAuth(EmailAuth email);
}
