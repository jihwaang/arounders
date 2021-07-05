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
public class ChatRoom {

    private Long id;
    private String title;
    private String region;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    private Long memberId;
    private Integer cityId;
    private Long boardId;

}
