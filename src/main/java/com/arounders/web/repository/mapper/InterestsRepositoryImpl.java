package com.arounders.web.repository.mapper;

import com.arounders.web.entity.Interests;
import com.arounders.web.repository.InterestsRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class InterestsRepositoryImpl implements InterestsRepository {

    private final InterestsRepository mapper;

    public InterestsRepositoryImpl(SqlSession sqlSession) {
        this.mapper = sqlSession.getMapper(InterestsRepository.class);
    }

    @Override
    public int getCountOfMember(Long memberId) {
        return mapper.getCountOfMember(memberId);
    }

    @Override
    public int getCountOfBoard(Long boardId) {
        return mapper.getCountOfBoard(boardId);
    }

    @Override
    public int insert(Interests interests) {

        return mapper.insert(interests);
    }

    @Override
    public int delete(Interests interests) {

        return mapper.delete(interests);
    }

    @Override
    public int getExsistingCount(Interests interests) {
        return mapper.getExsistingCount(interests);
    }
}
