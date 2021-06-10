package com.arounders.web.service;

import com.arounders.web.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<Board> getList();

    Optional<Board> getBoard(Optional<Integer> id);

    int createBoard(Optional<Board> board);

    int editBoard(Optional<Board> board);

    int removeBoard(Optional<Integer> id);


}
