package com.arounders.web.service;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Member;
import org.springframework.web.multipart.MultipartFile;

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

    Integer isOverlapped(String field, String value);

    default Member toEntity(MemberDTO memberDTO) {
        return Member.builder()
                        .id((memberDTO.getId()))
                        .email(memberDTO.getEmail())
                        .password(memberDTO.getPassword())
                        .nickname(memberDTO.getNickname())
                        .phone(memberDTO.getPhone())
                        .addr(memberDTO.getAddr())
                        .addrDtl(memberDTO.getAddrDtl())
                        .roleId(memberDTO.getRoleId())
                        .cityId(memberDTO.getCityId())
                    .build();
    }


    Member getMemberByEmail(String email);

    int updateLastLogin(Member user);

    Integer countByEmailandNickName(MemberDTO member);

    int updatePassword(MemberDTO member);

    int updateAddress(MemberDTO member);

    Integer findCityId(String addr);

    int updateMember(MemberDTO member, MultipartFile multipartFile, String realPath);
}
