package com.arounders.web.repository;

import com.arounders.web.dto.ChatMemberDTO;
import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMemberRepository {

    /* 채팅방 참여 */
    int insert(@Param("memberId") Long memberId, @Param("chatRoomId") Long chatRoomId);
    /* 채팅방 탈퇴 */
    int delete(@Param("memberId") Long memberId, @Param("chatRoomId") Long chatRoomId);
    /* 특정 유저가 채팅방 가입했는지 여부 조회 */
    int isJoin(@Param("memberId") Long memberId, @Param("chatRoomId") Long chatRoomId);
    /* 특정 유저가 가입한 채팅방 목록 조회 */
    List<ChatRoomDTO> findAllByMemberId(@Param("memberId") Long memberId);
    /* 특정 채팅방의 참여자 목록 */
    List<ChatMemberDTO> findByChatRoomId(Long chatRoomId);
}
