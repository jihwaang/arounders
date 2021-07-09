package com.arounders.web.repository.mapper;

import com.arounders.web.dto.ReviewDTO;
import com.arounders.web.entity.Review;
import com.arounders.web.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
@Slf4j
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewRepository mapper;

    public ReviewRepositoryImpl(SqlSession sqlSession) {
        this.mapper = sqlSession.getMapper(ReviewRepository.class);
    }

    @Override
    public List<ReviewDTO> getReviewListOfBoard(Long boardId, Long offset) {
        return mapper.getReviewListOfBoard(boardId, offset);
    }

    @Override
    public List<ReviewDTO> getReviewListOfMember(Long memberId, Long offset) {
        return mapper.getReviewListOfMember(memberId, offset);
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

    @Override
    public ReviewDTO findUserReview(Review review) {
        return mapper.findUserReview(review);
    }

    @Override
    public int getCountDups(Review review) {
        return mapper.getCountDups(review);
    }
}
