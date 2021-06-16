package com.arounders.web.repository.mapper;

import com.arounders.web.entity.EmailAuth;
import com.arounders.web.repository.EmailAuthRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class EmailAuthRepositoryImpl implements EmailAuthRepository {

    private EmailAuthRepository mapper;

    public EmailAuthRepositoryImpl(SqlSession sqlSession) {
        this.mapper = sqlSession.getMapper(EmailAuthRepository.class);
    }

    @Override
    public EmailAuth findEmailByEmail(EmailAuth email) {
        return mapper.findEmailByEmail(email);
    }

    @Override
    public EmailAuth findByConfirmKey(EmailAuth email) {
        return mapper.findByConfirmKey(email);
    }

    @Override
    public String findConfirmKeyById(EmailAuth email) {
        return mapper.findConfirmKeyById(email);
    }

    @Override
    public Long updateByKey(String confirmKey) {
        return mapper.updateByKey(confirmKey);
    }

    @Override
    public Long getCountConfirmed(String confirmKey) {
        return mapper.getCountConfirmed(confirmKey);
    }

    @Override
    public Long insert(EmailAuth email) {
        Long result = mapper.insert(email);
        return email.getId();
    }

    @Override
    public Long update(EmailAuth email) {
        Long result = mapper.update(email);
        return email.getId();
    }

    @Override
    public String findConfirmKeyByEmail(EmailAuth email) {
        return mapper.findConfirmKeyByEmail(email);
    }
}
