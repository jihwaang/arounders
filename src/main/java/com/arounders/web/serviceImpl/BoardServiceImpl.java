package com.arounders.web.serviceImpl;

import com.arounders.web.entity.Board;
import com.arounders.web.repository.BoardRepository;
import com.arounders.web.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> getList() {
        return boardRepository.getList();
    }

    @Override
    public Optional<Board> getBoard(Optional<Integer> id) {
        return boardRepository.getBoard(id);
    }

    @Override
    public int createBoard(Optional<Board> board) {
        return boardRepository.insert(board);
    }

    @Override
    public int editBoard(Optional<Board> board) {
        return boardRepository.update(board);
    }

    @Override
    public int removeBoard(Optional<Integer> id) {
        return boardRepository.delete(id);
    }
}
