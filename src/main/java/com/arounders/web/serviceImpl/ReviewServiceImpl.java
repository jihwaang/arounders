package com.arounders.web.serviceImpl;

import com.arounders.web.dto.ReviewDTO;
import com.arounders.web.entity.Review;
import com.arounders.web.repository.ReviewRepository;
import com.arounders.web.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewDTO> getReviewListOfBoard(Long boardId, Long offset) {
        return reviewRepository.getReviewListOfBoard(boardId, offset);
    }

    @Override
    public List<ReviewDTO> getReviewListOfMember(Long memberId) {
        return reviewRepository.getReviewListOfMember(memberId);
    }

    @Override
    public Long createReview(ReviewDTO reviewDTO) {

        Review review = dtoToEntity(reviewDTO);
        int result = reviewRepository.insert(review);

        return result > 0? review.getId() : null;
    }

    @Override
    public Long editReview(ReviewDTO reviewDTO) {

        Review review = dtoToEntity(reviewDTO);
        int result = reviewRepository.update(review);

        return result > 0? review.getId() : null;
    }

    @Override
    public Long removeReview(Long reviewId) {

        int result = reviewRepository.delete(reviewId);

        return result > 0? reviewId : null;
    }

    @Override
    public ReviewDTO getReviewOfMineOfBoard(Long id) {
        Review review = Review.builder().id(id).build();
        return reviewRepository.findUserReview(review);
    }
}
