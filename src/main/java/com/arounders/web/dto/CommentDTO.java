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

    /* mypage에서 사용될 댓글달린 게시글의 제목 */
    private String title;

}
