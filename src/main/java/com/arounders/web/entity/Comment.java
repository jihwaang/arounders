package com.arounders.web.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {

    private Long id;
    private String content;
    private Integer isHidden;
    private Long upperId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long memberId;
    private Long boardId;

}
