package com.arounders.web.repository.mapper;

import com.arounders.web.entity.Member;
import com.arounders.web.repository.MemberRepository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberRepository memberRepository;

    public MemberRepositoryImpl(SqlSession sqlSession) {
        this.memberRepository = sqlSession.getMapper(MemberRepository.class);
    }

    @Override
    public Member getMember(Long id) {
        return memberRepository.getMember(id);
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.getMembers();
    }

    @Override
    public int insert(Member member) {
        return memberRepository.insert(member);
    }

    @Override
    public int update(Member member) {
        return memberRepository.update(member);
    }

    @Override
    public int delete(Long id) {
        return memberRepository.delete(id);
    }
}
