package com.arounders.web.repository;

import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.ChatRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatRoomRepository {

    /* 채팅방 목록 조회 */
    List<ChatRoomDTO> findAllByRegionAndCityId(@Param("region") String region, @Param("cityId") Integer cityId);
    /* 채팅방 개설 */
    int insert(ChatRoom chatRoom);
    /* 채팅방 조회 */
    ChatRoomDTO findById(Long id);
    ChatRoomDTO findByBoardId(Long boardId);
    /* 채팅방 종료 */
    int close(Long id);
    /* 채팅방 변경 */
    int update(ChatRoom chatRoom);

}
