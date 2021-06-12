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
public class Attachment {

    private String id;
    private String name;
    private String path;
    private LocalDateTime createdAt;

    private Long memberId;
    private Long boardId;

}

