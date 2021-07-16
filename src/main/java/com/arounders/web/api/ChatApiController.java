package com.arounders.web.api;

import com.arounders.web.dto.BufferMap;
import com.arounders.web.dto.ChatDTO;
import com.arounders.web.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/chats/api/v1")
public class ChatApiController {

    private final HttpServletRequest request;
    private final ChatService service;
    private final BufferMap<Long, List<ChatDTO>> chatListMap;

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<String>> getChats(@PathVariable("chatRoomId") Long id){

        List<ChatDTO> chatDTOs = chatListMap.get(id);

        if(chatDTOs != null && chatDTOs.size() > 0){

            /* Buffer의 내용 save 후 clear */
            service.save(id, chatDTOs, request.getServletContext().getRealPath("/chat"));
            chatDTOs.clear();
        }

        List<String> chats = service.getChats(id, request.getServletContext().getRealPath("/chat"));

        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

}
