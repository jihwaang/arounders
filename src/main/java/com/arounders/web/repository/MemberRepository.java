package com.arounders.web.repository;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    /* 사용자 도시 아이디 조회 */
    Long findCityIdByAddr(String address);

    Integer getCount(String field, String value);

    Member findMemberByEmail(String email);

    int updateLastLogin(Member user);

    Integer countByEmailandNickName(MemberDTO member);

    int updatePassword(Member member);

    int updateAddress(Member member);

    Integer findCityId(String addr);

    Integer countByEmail(String email);
}
