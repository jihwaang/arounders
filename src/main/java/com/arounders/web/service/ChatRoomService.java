package com.arounders.web.service;

import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.ChatRoom;
import com.arounders.web.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatRoomService {

    /* 채팅방 목록 조회 */
    List<ChatRoomDTO> getChatRoomList(String region, Integer cityId);
    /* 채팅방 개설 */
    Long create(ChatRoomDTO chatRoomDTO);
    /* 채팅방 조회 */
    ChatRoomDTO get(Long id);
    ChatRoomDTO getByBoardId(Long boardId);
    /* 채팅방 종료 */
    Long close(Long id);
    /* 채팅방 변경 */
    Long update(ChatRoomDTO chatRoomDTO);

    /* 채팅방 파일로 생성 */
    void chatRoomToFile(ChatRoom chatRoom);

    default ChatRoom dtoToEntity(ChatRoomDTO dto){
        return ChatRoom.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .region(dto.getRegion())
                .cityId(dto.getCityId())
                .memberId(dto.getMemberId())
                .boardId(dto.getBoardId())
                .createdAt(dto.getCreatedAt())
                .finishedAt(dto.getFinishedAt())
                .build();
    }
    default ChatRoomDTO entityToDTO(ChatRoom entity, String city, String creator){
        return ChatRoomDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .region(entity.getRegion())
                .cityId(entity.getCityId())
                .city(city)
                .creator(creator)
                .memberId(entity.getMemberId())
                .finishedAt(entity.getFinishedAt())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
