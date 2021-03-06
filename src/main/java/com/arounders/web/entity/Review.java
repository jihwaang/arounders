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
public class Review {

    private Long id;
    private Integer rate;
    private String content;
    private LocalDateTime createdAt;

    private Long memberId;
    private Long boardId;

}
