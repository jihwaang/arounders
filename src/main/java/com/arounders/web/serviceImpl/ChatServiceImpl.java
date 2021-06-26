package com.arounders.web.serviceImpl;

import com.arounders.web.dto.ChatDTO;
import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.repository.ChatRepository;
import com.arounders.web.repository.ChatRoomRepository;
import com.arounders.web.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository repository;
    private final ChatRoomRepository roomRepository;


    @Override
    public void save(Long chatRoomId, List<ChatDTO> list) {

        if(list.isEmpty() || list.get(0).getMemberId() == null) return;

        List<String> jsonList = list.stream().map(c -> dtoToJson(c)).collect(Collectors.toList());

        ChatRoomDTO room = roomRepository.findById(chatRoomId);

        repository.save(room, jsonList);
    }

    @Override
    public List<String> getChats(Long id) {

        return repository.getChats(roomRepository.findById(id));
    }
}
