package com.arounders.web.serviceImpl;

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
    public List<Review> getReviewListOfBoard(Integer boardId) {
        return reviewRepository.getReviewListOfBoard(boardId);
    }

    @Override
    public List<Review> getReviewListOfMember(Integer memberId) {
        return reviewRepository.getReviewListOfMember(memberId);
    }

    @Override
    public int createReview(Review review) {
        return reviewRepository.insert(review);
    }

    @Override
    public int editReview(Review review) {
        return reviewRepository.update(review);
    }

    @Override
    public int removeReview(Review review) {
        return reviewRepository.delete(review);
    }
}
