package com.arounders.web.controller;

import com.arounders.web.entity.Board;
import com.arounders.web.service.BoardService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@Slf4j
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
    public String createBoard(Model model, @RequestBody Board board) {
        log.info("request board : {}", board);
        int result = boardService.createBoard(board);
        log.info("generated id : {}", board.getId());
        model.addAttribute("id", board.getId());
        return "";
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
