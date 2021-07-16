package com.arounders.web.repository;

import com.arounders.web.dto.ChatRoomDTO;

import java.util.List;

public interface ChatRepository {

    /* 채팅 저장 */
    void save(ChatRoomDTO room, List<String> list, String realPath);
    /* 채팅 불러오기 */
    List<String> getChats(ChatRoomDTO room, String realPath);
}
