package com.arounders.web.repository;

import com.arounders.web.entity.Attachment;
import com.arounders.web.entity.Member;

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

    /* 프로필 등록 */
    int insertProfileImage(Attachment attachment);

    /* 프로필 사진 경로 조회 */
    String findProfileImgPathById(Long id);

    /* 프로필 사진 삭제 */
    int deleteProfileImage(Member member);
}
