package com.arounders.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long upperId;

    private Long boardId;
    private Long memberId;
    private String nickname;

    /* 게시글 제목 */
    private String title;

    /* 대댓글 여부 */
    private int hasChild;
}