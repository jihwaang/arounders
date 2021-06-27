package com.arounders.web.serviceImpl;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Member;
import com.arounders.web.repository.MemberRepository;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.MailService;
import com.arounders.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    private final AttachmentService attachmentService;

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
        member = encodePassword(member);
        member.setCityId();
        //member.setPassword(passwordEncoder.encode(member.getPassword()));
        int result = memberRepository.insert(member);
        return result == 1? member.getId() : null;
    }



    @Override
    public Long update(Member member) {

        log.info("#MemberService : update -> " + member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        int result = memberRepository.update(member);

        return result == 1? member.getId() : null;
    }

    @Override
    public Long dropOut(Long id) {
        log.info("#MemberService : drop out -> " + id + "번 회원님이 탈퇴했습니다.");
        int result = memberRepository.delete(id);

        return result == 1? id : null;
    }

    @Override
    public Integer isOverlapped(String field, String value) {
        return memberRepository.getCount(field, value);
    }

    @Override
    public Member getMemberByEmail(String email) {
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

    @Transactional
    @Override
    public int updatePassword(MemberDTO member) {
        Member memberEntity = toEntity(member);
        //generate new password here
        memberEntity.generateNewPassword();
        log.info("new password: {}", memberEntity.getPassword());

        //send new password here by email
        int mailResult = sendNewPassword(memberEntity);

        //encrypt new password here to save to database here
        memberEntity = encodePassword(memberEntity);

        int updateResult = memberRepository.updatePassword(memberEntity);

        return (mailResult == 1 && updateResult == 1) ? 1 : -1;
    }

    @Override
    public int updateAddress(MemberDTO requestMember) {
        Member member =  toEntity(requestMember);
        return memberRepository.updateAddress(member);
    }

    @Override
    public Integer findCityId(String addr) {
        return memberRepository.findCityId(addr);
    }

    @Transactional
    @Override
    public int updateMember(MemberDTO memberDTO, MultipartFile multipartFile, String realPath) {
        /* update Member data here */
        Member member = toEntity(memberDTO);
        /* check if password should not be changed */
        if (member.getPassword() != null && !member.getPassword().isBlank()) {
            member = encodePassword(member);
        } else {
            member.setPassword(null);
        }
        int memberResult = memberRepository.update(member);
        /* save profile image then insert into database here if existing */
        int fileResult = 0;
        if (multipartFile.getSize() == 0) {
            fileResult = 1;
        } else {
            int result = attachmentService.deleteProfileImage(member);
            log.info("deleted profile image result: {}", result);
            fileResult = attachmentService.saveOneFile(multipartFile, realPath, member);
        }

        return (memberResult == 1 && fileResult == 1) ? 1 : 0;
    }

    @Override
    public int countByEmail(String email) {
        return (int) memberRepository.countByEmail(email);
    }

    public Member encodePassword(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return member;
    }

    public int sendNewPassword(Member member) {
        return mailService.sendNewPassword(member);
    }
}
