package com.arounders.web.repository;

import com.arounders.web.entity.Member;

import java.util.List;

public interface MemberRepository {

    /* 사용자 조회 */
    Member getMember(Long id);
    /* 사용자 목록 조회 */
    List<Member> getMembers();
    /* 사용자 생성 */
    int insert(Member member);
    /* 사용자 수정 */
    int update(Member member);
    /* 사용자 삭제 */
    int delete(Long id);
}
