package com.arounders.web.repository.mapper;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import com.arounders.web.entity.Board;
import com.arounders.web.repository.BoardRepository;
import org.apache.ibatis.annotations.Param;
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
    public List<BoardDTO> getList(Long memberId, BoardCriteria criteria) {
        return mapper.getList(memberId, criteria);
    }

    @Override
    public BoardDTO getBoard(Long id, Long memberId) {
        return mapper.getBoard(id, memberId);
    }

    @Override
    public int insert(Board board) {
        return mapper.insert(board);
    }

    @Override
    public int update(Board board) { return mapper.update(board); }

    @Override
    public int delete(Long id) {
        return mapper.delete(id);
    }

    @Override
    public int hide(Long id) {
        return mapper.hide(id);
    }

    @Override
    public int show(Long id) {
        return mapper.show(id);
    }

    @Override
    public int done(Long boardId) {
        return mapper.done(boardId);
    }

    /* About Mypage */
    @Override
    public List<BoardDTO> getMyList(BoardCriteria criteria, Long memberId) {
        return mapper.getMyList(criteria, memberId);
    }

    @Override
    public Integer getCountByCategory(Long memberId, Integer categoryId) {
        return mapper.getCountByCategory(memberId, categoryId);
    }
}
