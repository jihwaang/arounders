package com.arounders.web.service;

import com.arounders.web.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<Board> getList();

    Board getBoard(Integer id);

    int createBoard(Board board);

    int editBoard(Board board);

    int removeBoard(Integer id);


}
