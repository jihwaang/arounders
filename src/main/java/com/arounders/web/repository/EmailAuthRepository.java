package com.arounders.web.repository;

import com.arounders.web.entity.EmailAuth;

public interface EmailAuthRepository {
    EmailAuth findEmailByEmail(EmailAuth email);

    Long insert(EmailAuth email);

    Long update(EmailAuth email);
}
