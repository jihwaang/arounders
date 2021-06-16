package com.arounders.web.repository;

import com.arounders.web.entity.EmailAuth;

public interface EmailAuthRepository {
    EmailAuth findEmailByEmail(EmailAuth email);

    EmailAuth findByConfirmKey(EmailAuth email);

    Long insert(EmailAuth email);

    Long update(EmailAuth email);

    String findConfirmKeyByEmail(EmailAuth email);

    String findConfirmKeyById(EmailAuth email);

    Long updateByKey(String confirmKey);

    Long getCountConfirmed(String confirmKey);
}
