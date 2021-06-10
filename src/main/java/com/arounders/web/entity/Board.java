package com.arounders.web.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Board {
    private Long id;
    private String title;
    private String content;
    private String region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer isHidden;
    private String thumbnailName;
    private String thumbnailPath;

    private Long memberId;
    private Long categoryId;
}