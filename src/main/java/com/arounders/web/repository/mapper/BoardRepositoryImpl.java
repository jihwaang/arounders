package com.arounders.web.repository.mapper;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.entity.Board;
import com.arounders.web.repository.BoardRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardRepository mapper;

    public BoardRepositoryImpl(SqlSession sqlSession) {
        mapper = sqlSession.getMapper(BoardRepository.class);
    }

    @Override
    public List<BoardDTO> getHiddenList() {
        return mapper.getHiddenList();
    }

    @Override
    public List<BoardDTO> getList() {
        return mapper.getList();
    }

    @Override
    public BoardDTO getBoard(Long id) {
        return mapper.getBoard(id);
    }

    @Override
    public int insert(Board board) {
        return mapper.insert(board);
    }

    @Override
    public int update(Board board) {
        return mapper.update(board);
    }

    @Override
    public int delete(Long id) {
        return mapper.delete(id);
    }

}
