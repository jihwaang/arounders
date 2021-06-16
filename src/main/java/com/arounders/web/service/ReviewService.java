package com.arounders.web.service;

import com.arounders.web.dto.ReviewDTO;
import com.arounders.web.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewListOfBoard(Long boardId);

    List<ReviewDTO> getReviewListOfMember(Long memberId);

    Long createReview(ReviewDTO review);

    Long editReview(ReviewDTO review);

    Long removeReview(Long reviewId);

    default Review dtoToEntity(ReviewDTO dto) {
        return Review.builder()
                .id(dto.getId())
                .rate(dto.getRate())
                .content(dto.getContent())
                .memberId(dto.getMemberId())
                .boardId(dto.getBoardId())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
