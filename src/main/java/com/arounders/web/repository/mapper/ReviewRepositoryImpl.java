package com.arounders.web.repository.mapper;

import com.arounders.web.dto.ReviewDTO;
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
    public List<ReviewDTO> getReviewListOfBoard(Long boardId) {
        return mapper.getReviewListOfBoard(boardId);
    }

    @Override
    public List<ReviewDTO> getReviewListOfMember(Long memberId) {
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
    public int delete(Long reviewId) {
        return mapper.delete(reviewId);
    }
}
