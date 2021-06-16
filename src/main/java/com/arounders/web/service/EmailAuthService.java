package com.arounders.web.service;

import com.arounders.web.entity.EmailAuth;

public interface EmailAuthService {
    EmailAuth getAuthEmail(EmailAuth email);

    String requestAuth(EmailAuth email);

    Long confirmAuth(EmailAuth email);

    Long confirmAuthByKey(String confirmKey);

    Long checkDuplicatedConfirm(String confirmKey);

    EmailAuth findByConfirmKey(EmailAuth emailAuth);
}
