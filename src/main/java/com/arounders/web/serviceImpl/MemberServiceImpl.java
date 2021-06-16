package com.arounders.web.serviceImpl;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Member;
import com.arounders.web.repository.MemberRepository;
import com.arounders.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member getMember(Long id) {

        Member member = memberRepository.getMember(id);
        log.info("#MemberService : getMember -> " + member);

        return member;
    }

    @Override
    public List<Member> getMembers() {

        return memberRepository.getMembers();
    }

    @Override
    public Long signup(MemberDTO requestMember) {

        log.info("#MemberService : signup -> " + requestMember);
        Member member = toEntity(requestMember);
        int result = memberRepository.insert(member);
        return result == 1? member.getId() : null;
    }

    @Override
    public Long update(Member member) {

        log.info("#MemberService : update -> " + member);
        int result = memberRepository.update(member);

        return result == 1? member.getId() : null;
    }

    @Override
    public Long dropOut(Long id) {
        log.info("#MemberService : drop out -> " + id + "번 회원님이 탈퇴했습니다.");
        int result = memberRepository.delete(id);

        return result == 1? id : null;
    }
}
