package com.arounders.web.repository.mapper;

import com.arounders.web.entity.Member;
import com.arounders.web.repository.MemberRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberRepository memberRepository;

    public MemberRepositoryImpl(SqlSession sqlSession){
        this.memberRepository = sqlSession.getMapper(MemberRepository.class);
    }

    @Override
    public Member getMember(Long id) {
        return memberRepository.getMember(id);
    }
}
