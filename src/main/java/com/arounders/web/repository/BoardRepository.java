package com.arounders.web.repository;

import com.arounders.web.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    List<Board> getList();

    Optional<Board> getBoard(Optional<Integer> id);

    int insert(Optional<Board> board);

    int update(Optional<Board> board);

    int delete(Optional<Integer> id);
}
