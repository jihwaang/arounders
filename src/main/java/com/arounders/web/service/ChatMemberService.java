package com.arounders.web.service;

import com.arounders.web.dto.ChatMemberDTO;
import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMemberService {

    /* 채팅방 참여 */
    Long join(@Param("memberId") Long memberId, Long chatRoomId);
    /* 채팅방 탈퇴 */
    Long dropOut(@Param("memberId") Long memberId, Long chatRoomId);
    /* 특정 유저가 채팅방 가입했는지 여부 조회 */
    int isJoin(@Param("memberId") Long memberId, Long chatRoomId);
    /* 특정 유저가 가입한 채팅방 목록 조회 */
    List<ChatRoomDTO> getMyChatRooms(Long memberId);
    /* 특정 채팅방의 참여자 목록 */
    List<ChatMemberDTO> getClientsFromChatRoom(Long chatRoomId);
}
