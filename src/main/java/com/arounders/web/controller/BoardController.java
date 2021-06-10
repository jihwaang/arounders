package com.arounders.web.controller;

import com.arounders.web.entity.Board;
import com.arounders.web.service.BoardService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping("/getList")
    public List<Board> getList() {
        List<Board> list = boardService.getList();
        return list;
    }

    @GetMapping("/getBoard")
    public Optional<Board> getBoard(@PathVariable Integer id) {
        log.info("getBoard init == id : {}", id.toString());
        Optional<Integer> tmp = Optional.of(id);
        Optional<Board> board = boardService.getBoard(tmp);
        return board;
    }

    @PostMapping("/createBoard")
    public int createBoard(Optional<Board> board) {
        int result = boardService.createBoard(board);
        return result;
    }

    @PutMapping("/editBoard")
    public int editBoard(Optional<Board> board) {
        int result = boardService.editBoard(board);
        return result;
    }

    @DeleteMapping("/removeBoard")
    public int removeBoard(@PathVariable Optional<Integer> id) {
        int result = boardService.removeBoard(id);
        return result;
    }


}
