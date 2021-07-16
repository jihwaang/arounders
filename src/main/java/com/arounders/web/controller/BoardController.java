package com.arounders.web.controller;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import com.arounders.web.entity.Attachment;
import com.arounders.web.entity.Board;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.BoardService;
import com.arounders.web.service.ChatRoomService;
import com.arounders.web.serviceImpl.AttachmentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final BoardService boardService;
    private final ChatRoomService chatRoomService;
    private final AttachmentService attachmentService;
    private final HttpSession session;

    @GetMapping("/list")
    public String getList(Model model) {

        return "board/list";
    }

    @GetMapping("/read")
    public String getBoard(Long id, Model model) {

        Long memberId = (Long) session.getAttribute("id");
        BoardDTO boardDTO = boardService.getBoard(id, memberId);
        List<Attachment> attachments = attachmentService.getBoardAttachments(id);

        boardDTO.setAttachments(attachments);

        log.info("#BoardController -> getBoard : " + boardDTO);

        /* if(모집) model add chatroom */
        if(boardDTO.getCategoryId() == 3){
            ChatRoomDTO chatRoom = chatRoomService.getByBoardId(id);
            model.addAttribute("room", chatRoom);
        }

        model.addAttribute("board", boardDTO);

        return "board/read";
    }

    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("board", new BoardDTO());

        return "board/register";
    }
    @PostMapping("/register")
    public String createBoard(BoardDTO boardDTO,
                              @RequestParam(name = "file") MultipartFile[] postFiles,
                              @RequestParam(name = "thumbIdx", defaultValue = "0") Integer thumbIdx,
                              HttpServletRequest request,
                              RedirectAttributes rttr) {

        /* test */
        //Long memberId = 12L;
        /* develop */
        Long memberId = (Long) session.getAttribute("id");
        String region = (String) session.getAttribute("region");
        Integer cityId = (Integer) session.getAttribute("cityId");

        boardDTO.setMemberId(memberId);
        boardDTO.setRegion(region);
        boardDTO.setCityId(cityId);

        String realPath = request.getServletContext().getRealPath("/upload");

        List<Attachment> attachments = attachmentService.attachmentsProcess(postFiles, boardDTO, realPath, memberId, thumbIdx);

        log.info("#BoardController -> createBoard : " +  boardDTO);
        log.info(Arrays.toString(postFiles));
        log.info("thumbIdx : " + thumbIdx);

        /* Board -> DB */
        Long boardId = boardService.createBoard(boardDTO);

        /* Attachment -> DB */
        if(attachments != null){
            attachments.forEach(attch -> attch.setBoardId(boardId));
            attachmentService.createAttachment(attachments);
        }

        /* 모집? 채팅방 개설 */
        if(boardDTO.getCategoryId() == 3){
            chatRoomService.create(ChatRoomDTO.builder()
            .boardId(boardId)
            .title(boardDTO.getTitle())
            .region(boardDTO.getRegion())
            .city(boardDTO.getCity())
            .cityId(boardDTO.getCityId())
            .memberId(boardDTO.getMemberId())
            .build(), request.getServletContext().getRealPath("/chat"));
        }

        return "redirect:/board/list";
    }

    @GetMapping("/edit")
    public String editBoard(Long id, Model model){

        Long memberId = (Long) session.getAttribute("id");
        BoardDTO boardDTO = boardService.getBoard(id, memberId);
        List<Attachment> attachments = attachmentService.getBoardAttachments(id);

        boardDTO.setAttachments(attachments);

        log.info("#BoardController -> editBoard : " + boardDTO);

        model.addAttribute("board", boardDTO);

        return "board/edit";
    }
    @PostMapping("/edit")
    public String editBoard(BoardDTO boardDTO,
                            @RequestParam("page") Integer page,
                            @RequestParam("field") String field,
                            @RequestParam("keyword") String keyword,
                            @RequestParam("categoryParam") String categoryParam,
                            @RequestParam("order") String order,
                            @RequestParam("statusParam") String statusParam,
                            @RequestParam(name = "file") MultipartFile[] postFiles,
                            @RequestParam(name = "thumbIdx", defaultValue = "0") Integer thumbIdx,
                            HttpServletRequest request,
                            RedirectAttributes rttr) {

        String realPath = request.getServletContext().getRealPath("/upload");

        if(postFiles[0].getSize() > 0) {
            List<Attachment> attachments = attachmentService.attachmentsProcess(postFiles, boardDTO, realPath, boardDTO.getMemberId(), thumbIdx);

            attachments.forEach(attach -> attach.setBoardId(boardDTO.getId()));
            /* Attachment -> DB */
            attachmentService.removeAttachment(boardDTO.getId());
            attachmentService.createAttachment(attachments);
        }

        log.info("#BoardController -> editBoard : " +  boardDTO);
        log.info(Arrays.toString(postFiles));
        log.info("thumbIdx : " + thumbIdx);

        Long boardId = boardService.editBoard(boardDTO);

        rttr.addAttribute("id", boardId);
        rttr.addAttribute("page", page);
        rttr.addAttribute("field", field);
        rttr.addAttribute("keyword", keyword);
        rttr.addAttribute("status", statusParam);
        rttr.addAttribute("order", order);
        rttr.addAttribute("category", categoryParam);

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String removeBoard(Long id, RedirectAttributes rttr) {

        log.info("#BoardController -> removeBoard : " + id);
        Long boardId = boardService.removeBoard(id);
        rttr.addAttribute("boardId", id);

        return "redirect:/board/list";
    }

    @GetMapping("/review")
    public String getReviews(Model model, @RequestParam(value = "boardId") Long id) {
        log.info("#BoardController -> boardId : " + id);
        model.addAttribute("boardId", id);
        return "board/review";
    }


}
