package com.arounders.web.repository;

import com.arounders.web.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    List<Board> getList();

    Board getBoard(Integer id);

    int insert(Board board);

    int update(Board board);

    int delete(Integer id);
}
