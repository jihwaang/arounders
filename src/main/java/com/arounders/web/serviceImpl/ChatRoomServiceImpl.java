package com.arounders.web.serviceImpl;

import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.ChatRoom;
import com.arounders.web.repository.ChatMemberRepository;
import com.arounders.web.repository.ChatRoomRepository;
import com.arounders.web.service.ChatRoomService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository repository;
    private final ChatMemberRepository chatMemberRepository;

    @Override
    public List<ChatRoomDTO> getChatRoomList(String region, Integer cityId) {
        return repository.findAllByRegionAndCityId(region, cityId);
    }

    @Override
    @Transactional
    public Long create(ChatRoomDTO chatRoomDTO) {

        ChatRoom chatRoom = dtoToEntity(chatRoomDTO);

        int result = repository.insert(chatRoom);

        /* 채팅방 개설 -> 채팅방 참여 (auto) */
        if(result > 0){
            chatRoomToFile(chatRoom);
            chatMemberRepository.insert(chatRoom.getMemberId(), chatRoom.getId());
        }

        return result > 0 ? chatRoom.getId() : null;
    }

    @Override
    public ChatRoomDTO get(Long id) {

        return repository.findById(id);
    }

    @Override
    public ChatRoomDTO getByBoardId(Long boardId) {
        return repository.findByBoardId(boardId);
    }

    @Override
    public Long close(Long id) {

        int result = repository.close(id);

        return result > 0? id : null;
    }

    @Override
    public Long update(ChatRoomDTO chatRoomDTO) {

        ChatRoom chatRoom = dtoToEntity(chatRoomDTO);

        int result = repository.update(chatRoom);

        return result > 0? chatRoom.getId() : null;
    }

    /* About Local Storage */
    @Override
    public void chatRoomToFile(ChatRoom chatRoom) {

        StringBuilder sb = new StringBuilder("C:/chat");
        sb.append(File.separator).append(chatRoom.getCityId()).append(File.separator).append(chatRoom.getRegion())
                .append(File.separator);

        File chatPath = new File(sb.toString());

        if(!chatPath.exists())
            chatPath.mkdirs();

        String chatRoomName = chatRoom.getId() + "_" + chatRoom.getTitle() + ".txt";
        File chatRoomFile = new File(sb.toString(), chatRoomName);

        if(!chatRoomFile.exists()) {
            try {
                chatRoomFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
