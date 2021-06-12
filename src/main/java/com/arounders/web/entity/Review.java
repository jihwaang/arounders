package com.arounders.web.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Review {

    private Long id;
    private Integer rate;
    private String content;
    private LocalDateTime createdAt;

    private Long memberId;
    private Long boardId;

}
