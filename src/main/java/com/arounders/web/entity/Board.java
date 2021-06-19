package com.arounders.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String status;

    private Long memberId;
    private Long categoryId;
    private Integer cityId;

}

