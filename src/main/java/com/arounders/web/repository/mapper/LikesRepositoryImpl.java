package com.arounders.web.repository.mapper;

import com.arounders.web.entity.Likes;
import com.arounders.web.repository.LikesRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class LikesRepositoryImpl implements LikesRepository{

    private final LikesRepository repository;

    public LikesRepositoryImpl(SqlSession sqlSession){
        this.repository = sqlSession.getMapper(LikesRepository.class);
    }

    @Override
    public int insert(Likes likes) {
        return repository.insert(likes);
    }

    @Override
    public int delete(Likes likes) {
        return repository.delete(likes);
    }

    @Override
    public int getCount(Long boardId) {
        return repository.getCount(boardId);
    }
}
