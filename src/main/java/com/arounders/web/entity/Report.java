package com.arounders.web.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Report {

    private Long id;
    private Integer isFinished;
    private LocalDateTime createdAt;

    private Long memberId;
    private Long boardId;
}
