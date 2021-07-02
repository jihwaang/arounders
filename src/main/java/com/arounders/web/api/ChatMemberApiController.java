package com.arounders.web.api;

import com.arounders.web.dto.ChatMemberDTO;
import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.Member;
import com.arounders.web.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/chatMember/api/v1")
public class ChatMemberApiController {

    private final ChatMemberService chatMemberService;
    private final HttpSession session;

    /* 채팅방 참여 */
    @PostMapping(value = "/{chatRoomId}")
    public ResponseEntity<Long> join(@PathVariable("chatRoomId") Long chatRoomId){

        /* test */
        //Long memberId = 12L;
        /* dev */
        Long memberId = (Long) session.getAttribute("id");

        Long id = chatMemberService.join(memberId, chatRoomId);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /* 채팅방 탈퇴 */
    @DeleteMapping(value = "/{chatRoomId}")
    public ResponseEntity<Long> dropOut(@PathVariable("chatRoomId") Long chatRoomId){

        /* test */
        //Long memberId = 12L;
        /* dev */
        Long memberId = (Long) session.getAttribute("id");

        Long id = chatMemberService.dropOut(memberId, chatRoomId);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* 특정 유저가 채팅방 가입했는지 여부 조회 */
    @GetMapping(value = "/{chatRoomId}")
    public ResponseEntity<Boolean> isJoin(@PathVariable("chatRoomId") Long chatRoomId){

        /* test */
        //Long memberId = 12L;
        /* dev */
        Long memberId = (Long) session.getAttribute("id");

        int result = chatMemberService.isJoin(memberId, chatRoomId);

        return new ResponseEntity<>(result > 0, HttpStatus.OK);
    }

    /* 특정 유저가 가입한 채팅방 목록 조회 */
    @GetMapping(value = "")
    public ResponseEntity<List<ChatRoomDTO>> getMyChatRooms(){

        /* test */
        //Long memberId = 12L;
        /* dev */
        Long memberId = (Long) session.getAttribute("id");

        List<ChatRoomDTO> rooms = chatMemberService.getMyChatRooms(memberId);

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    /* 특정 채팅방의 참여중인 유저 목록 조회(ID, Nickname) */
    @GetMapping(value = "/clients/{chatRoomId}")
    public ResponseEntity<List<ChatMemberDTO>> getClientsFromChatRoom(@PathVariable("chatRoomId") Long chatRoomId){

        return new ResponseEntity<>(chatMemberService.getClientsFromChatRoom(chatRoomId), HttpStatus.OK);
    }
}
