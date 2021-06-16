package com.arounders.web.repository;

import com.arounders.web.entity.Attachment;

import java.util.List;

public interface AttachmentRepository {
    Attachment getAttachment(Long id);

    int insert(List<Attachment> attachments);

    /* 특정 게시글의 파일 삭제 */
    int delete(Long boardId);

    /* 특정 게시글의 파일 목록 조회 */
    List<Attachment> findAttachesByBoardId(Long boardId);

    /* 특정 사용자의 프로필 */
    Attachment findAttachesByMemberId(Long memberId);
}
