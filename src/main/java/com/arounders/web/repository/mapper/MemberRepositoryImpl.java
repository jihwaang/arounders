package com.arounders.web.repository.mapper;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.City;
import com.arounders.web.entity.Member;
import com.arounders.web.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
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
    public List<Member> getMembers(int limit, int offset, String field, String keyword) {
        return memberRepository.getMembers(limit, offset, field, keyword);
    }

    @Override
    public Integer getValidMemberCount() {
        return memberRepository.getValidMemberCount();
    }

    @Override
    public Integer getTodayMemberCount() {
        return memberRepository.getTodayMemberCount();
    }

    @Override
    public Integer getInvalidMemberCount() {
        return memberRepository.getInvalidMemberCount();
    }

    @Override
    public int insert(Member member) {
        String address = member.getAddr().split(" ")[0];
        member.setCityId(City.valueOf(address).getCode());
        return memberRepository.insert(member);
    }

    @Override
    public int update(Member member) {
        //member.setPassword(passwordEncoder.encode(member.getPassword()));
        //member.setCityId(findCityId(member.getAddr()));
        return memberRepository.update(member);
    }

    @Override
    public int delete(Long id) {
        return memberRepository.delete(id);
    }

    @Override
    public Long findCityIdByAddr(String address) {
        return memberRepository.findCityIdByAddr(address);
    }

    @Override
    public Integer getCount(String field, String value) {
        Integer count = memberRepository.getCount(field, value);
        return count;
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public int updateLastLogin(Member user) {
        return memberRepository.updateLastLogin(user);
    }

    @Override
    public Integer countByEmailandNickName(MemberDTO member) {
        return memberRepository.countByEmailandNickName(member);
    }

    public Integer countByEmail(String email) {
        return memberRepository.countByEmail(email);
    }

    @Override
    public int checkPassword(String encodedPassword) {
        return memberRepository.checkPassword(encodedPassword);
    }

    @Override
    public String getPassword(Long id) {
        return memberRepository.getPassword(id);
    }

    @Override
    public int updatePassword(Member member) {
        return memberRepository.updatePassword(member);
    }

    @Override
    public int updateAddress(Member member) {
        return memberRepository.updateAddress(member);
    }

    @Override
    public Integer findCityId(String addr) {
        addr = addr.split(" ")[0];
        return City.valueOf(addr).getCode();
    }

}
