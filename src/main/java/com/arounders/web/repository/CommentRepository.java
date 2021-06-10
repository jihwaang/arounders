package com.arounders.web.repository;

import com.arounders.web.entity.Comment;

import java.util.List;

public interface CommentRepository {

    /* 댓글 조회 */
    Comment getComment(Long id);
    /* 특정 게시글의 댓글목록 조회 */
    List<Comment> getComments(Long boardId);
    /* 댓글 생성 */
    int insert(Comment comment);
    /* 댓글 수정 */
    int update(Comment comment);
    /* 댓글 삭제 */
    int delete(Long id);
}
