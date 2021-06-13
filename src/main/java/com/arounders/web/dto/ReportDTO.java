package com.arounders.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {

    /* Report */
    private Long id;
    private Integer isFinished;
    private LocalDateTime createdAt;

    /* Member */
    private Long memberId; //신고한 사람
    private String reporter;
    private String writer;

    /* Board */
    private Long boardId;
    private Long boardMemberId; //글을 쓴 사람 Id
    private LocalDateTime boardCreatedAt;
    private LocalDateTime boardUpdatedAt;
    private String title;
    private String region;
    private String categoryTitle;

}
