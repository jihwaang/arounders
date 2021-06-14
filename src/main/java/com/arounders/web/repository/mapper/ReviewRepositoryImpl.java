package com.arounders.web.repository.mapper;

import com.arounders.web.entity.Review;
import com.arounders.web.repository.ReviewRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewRepository mapper;

    public ReviewRepositoryImpl(SqlSession sqlSession) {
        this.mapper = sqlSession.getMapper(ReviewRepository.class);
    }

    @Override
    public List<Review> getReviewListOfBoard(Integer boardId) {
        return mapper.getReviewListOfBoard(boardId);
    }

    @Override
    public List<Review> getReviewListOfMember(Integer memberId) {
        return mapper.getReviewListOfMember(memberId);
    }

    @Override
    public int insert(Review review) {
        return mapper.insert(review);
    }

    @Override
    public int update(Review review) {
        return mapper.update(review);
    }

    @Override
    public int delete(Review review) {
        return mapper.delete(review);
    }
}
