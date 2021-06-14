package com.arounders.web.controller;

import com.arounders.web.entity.Board;
import com.arounders.web.service.BoardService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RequestMapping("/board")
@Slf4j
@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/getList")
    public String getList(Model model) {
        List<Board> list = boardService.getList();
        model.addAttribute("list", list);
        return "";
    }

    @GetMapping("/getBoard/{id}")
    public String getBoard(Model model, @PathVariable Integer id) {
        log.info("id : {}", id.toString());
        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "";
    }

    @PostMapping("/createBoard")
    @ResponseBody
    public String createBoard(Model model, Board board, @RequestParam(name = "file") MultipartFile[] postFiles, Integer thumbIdx, HttpServletRequest request) {

        log.info("request board : {}", board);
        log.info("request postFiles : {}", Arrays.toString(postFiles));
        log.info("request thumbIdx : {}", thumbIdx);

        String uploadPath = request.getServletContext().getRealPath("/upload");

        int boardResult = boardService.createBoard(board, postFiles, uploadPath, thumbIdx);

        log.info("generated id : {}", board.getId());
        model.addAttribute("id", board.getId());
        return "result.html";

    }


    @PutMapping("/editBoard")
    public String editBoard(Model model, @RequestBody Board board) {
        log.info("request board : {}", board);
        int result = boardService.editBoard(board);
        model.addAttribute("id", board.getId());
        return "";
    }

    @DeleteMapping("/removeBoard/{id}")
    public String removeBoard(Model model, @PathVariable Integer id) {
        log.info("request id : {}", id);
        int result = boardService.removeBoard(id);
        return "";
    }

}
