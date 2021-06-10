package com.arounders.web.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Attachment {
    private Long id;
    private String name;
    private String path;
    private LocalDateTime createdAt;

    private Long memberId;
    private Long boardId;
}
