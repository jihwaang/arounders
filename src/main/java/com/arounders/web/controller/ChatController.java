package com.arounders.web.controller;

import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.ChatRoom;
import com.arounders.web.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService service;

    @GetMapping(value = "/room")
    public String getChatRoom(Long id, Model model){

        ChatRoomDTO chatRoomDTO = service.get(id);
        model.addAttribute("room", chatRoomDTO);

        return "chat/room";
    }
    @PostMapping(value = "/room")
    public String deleteChatRoom(Long id, RedirectAttributes rttr) {

        Long chatRoomId = service.close(id);
        rttr.addFlashAttribute("chatRoodId", chatRoomId);

        return "redirect:/chat/list";
    }

    @GetMapping(value = "/list")
    public String chatList(Integer cityId, String region, Model model){

        cityId = 1;
        region = "용산구";

        List<ChatRoom> chatRoomList = service.getChatRoomList(region, cityId);
        model.addAttribute("list", chatRoomList);

        return "chat/list";
    }
    @PostMapping(value = "/list")
    public String createChatRoom(ChatRoomDTO chatRoomDTO, RedirectAttributes rttr){

        chatRoomDTO.setMemberId(12L);
        chatRoomDTO.setRegion("용산구");
        chatRoomDTO.setCityId(1);

        Long chatRoomId = service.create(chatRoomDTO);

//        System.out.println("chatRoomId = " + chatRoomId);
//        rttr.addFlashAttribute("chatRoomId", chatRoomId);

        return "redirect:/chat/list";
    }
}
