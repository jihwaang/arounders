package com.arounders.web.repository;

import com.arounders.web.dto.ChatRoomDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatRepositoryTest {

    @Autowired ChatRepository repository;
    @Autowired ChatRoomRepository roomRepository;

    @Test
    void getChatsTest(){

        ChatRoomDTO room = roomRepository.findById(3L);

        List<String> chats = repository.getChats(room);

        chats.forEach(System.out::println);
    }
}