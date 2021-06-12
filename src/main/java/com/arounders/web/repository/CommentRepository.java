package com.arounders.web.repository;

import com.arounders.web.dto.CommentDTO;
import com.arounders.web.entity.Comment;

import java.util.List;

public interface CommentRepository {

    /* 댓글 조회 */
    CommentDTO getComment(Long id);
    /* 특정 게시글의 댓글목록 조회 */
    List<CommentDTO> getComments(Long boardId);
    /* 내가 쓴 댓글목록 조회 */
    List<CommentDTO> getMyComments(Long id);
    /* 댓글 생성 */
    int insert(Comment commentDTO);
    /* 댓글 수정 */
    int update(Comment commentDTO);
    /* 댓글 삭제 */
    int delete(Long id);

}
