package com.arounders.web.service;

import com.arounders.web.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewListOfBoard(Integer boardId);

    List<Review> getReviewListOfMember(Integer memberId);

    int createReview(Review review);

    int editReview(Review review);

    int removeReview(Review review);
}
