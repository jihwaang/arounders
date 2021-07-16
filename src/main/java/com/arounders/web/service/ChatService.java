package com.arounders.web.service;

import com.arounders.web.dto.ChatDTO;
import com.arounders.web.dto.ChatRoomDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;

import java.util.List;

public interface ChatService {

    /* 채팅 저장 */
    void save(Long chatRoomId, List<ChatDTO> list, String realPath);
    /* 채팅 불러오기 */
    List<String> getChats(Long id, String realPath);

    default String dtoToJson(ChatDTO dto){
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("dto = " + dto);
        String chat = null;
        try {
            chat = mapper.writeValueAsString(dto);
            System.out.println("chat = " + chat);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return chat;
    }
}
