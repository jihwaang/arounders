package com.arounders.web.repository;

import com.arounders.web.dto.ReviewDTO;
import com.arounders.web.entity.Review;

import java.util.List;

public interface ReviewRepository {
    List<ReviewDTO> getReviewListOfBoard(Long boardId, Long offset);

    List<ReviewDTO> getReviewListOfMember(Long memberId);

    int insert(Review review);

    int update(Review review);

    int delete(Long reviewId);

    ReviewDTO findUserReview(Review review);
}
