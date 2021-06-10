package com.arounders.web.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatRoom {

    private Long id;
    private String title;
    private String region;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    private Long memberId;
}
