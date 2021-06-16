package com.arounders.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;
    private Integer rate;
    private String content;
    private LocalDateTime createdAt;

    private String writer;
    private String title;

    private Long memberId;
    private Long boardId;

}
