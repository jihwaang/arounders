package com.arounders.web.controller;

import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.ChatRoom;
import com.arounders.web.entity.City;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Log4j2
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService service;
    private final HttpSession session;

    @GetMapping(value = "/room")
    public String getChatRoom(Long id, Model model){

        ChatRoomDTO chatRoomDTO = service.get(id);
        model.addAttribute("room", chatRoomDTO);

        return "chat/room";
    }
    @PostMapping(value = "/room")
    public String deleteChatRoom(Long id, RedirectAttributes rttr) {

        Long chatRoomId = service.close(id);
        rttr.addAttribute("chatRoodId", chatRoomId);

        return "redirect:/chat/list";
    }

    @GetMapping(value = "/list")
    public String chatList(Model model){

        //cityId = 1;
        //region = "용산구";
        Integer cityId = (Integer) session.getAttribute("cityId");
        String region = (String) session.getAttribute("region");
        String city = City.getCity(cityId);

        List<ChatRoomDTO> chatRoomList = service.getChatRoomList(region, cityId);
        model.addAttribute("region", region);
        model.addAttribute("city", city);
        model.addAttribute("list", chatRoomList);

        return "chat/list";
    }
    @PostMapping(value = "/list")
    public String createChatRoom(ChatRoomDTO chatRoomDTO, RedirectAttributes rttr){

        Long memberId = (Long) session.getAttribute("id");
        Integer cityId = (Integer) session.getAttribute("cityId");
        String region = (String) session.getAttribute("region");

        chatRoomDTO.setMemberId(memberId);
        chatRoomDTO.setRegion(region);
        chatRoomDTO.setCityId(cityId);

        Long chatRoomId = service.create(chatRoomDTO);

        return "redirect:/chat/list";
    }
}
