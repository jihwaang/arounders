package com.arounders.web.repository.mapper;

import com.arounders.web.entity.Board;
import com.arounders.web.repository.BoardRepository;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardRepository mapper;

    public BoardRepositoryImpl(SqlSession sqlSession) {
        mapper = sqlSession.getMapper(BoardRepository.class);
    }


    @Override
    public List<Board> getList() {
        return mapper.getList();
    }

    @Override
    public Board getBoard(Integer id) {
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
    public int renew(String name, String path, Long id) {
        return mapper.renew(name, path, id);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

}
