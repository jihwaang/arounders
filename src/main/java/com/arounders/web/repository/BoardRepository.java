package com.arounders.web.repository;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    List<BoardDTO> getHiddenList();

    List<BoardDTO> getList();

    BoardDTO getBoard(Long id);

    int insert(Board board);

    int update(Board board);

    int delete(Long id);

}
