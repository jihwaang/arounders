package com.arounders.web.service;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Member;

import java.util.List;

public interface MemberService {

    /* 사용자 조회 */
    Member getMember(Long id);
    /* 사용자 목록 조회 */
    List<Member> getMembers();
    /* 사용자 생성 */
    Long signup(MemberDTO requestMember);
    /* 사용자 수정 */
    Long update(Member member);
    /* 사용자 삭제 */
    Long dropOut(Long id);

    default Member toEntity(MemberDTO memberDTO) {
        return Member.builder()
                        .email(memberDTO.getEmail())
                        .password(memberDTO.getPassword())
                        .nickname(memberDTO.getNickName())
                        .phone(memberDTO.getPhone())
                        .addr(memberDTO.getAddr())
                        .addrDtl(memberDTO.getAddrDtl())
                    .build();
    }

}
