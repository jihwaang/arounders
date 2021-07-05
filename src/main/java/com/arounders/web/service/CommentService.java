package com.arounders.web.service;

import com.arounders.web.dto.CommentDTO;
import com.arounders.web.dto.criteria.CommentCriteria;
import com.arounders.web.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {

    /* 댓글 조회 */
    CommentDTO getComment(Long id);
    /* 특정 게시글의 댓글목록 조회 */
    List<CommentDTO> getComments(Long boardId, CommentCriteria criteria);
    List<CommentDTO> getReComments(Long boardId, Long upperId);
    /* 내가 쓴 댓글목록 조회 */
    List<CommentDTO> getMyComments(Long memberId, CommentCriteria criteria);
    /* 댓글 생성 */
    Long create(CommentDTO commentDTO);
    /* 댓글 수정 */
    Long update(CommentDTO commentDTO);
    /* 댓글 삭제 */
    Long delete(Long id);
    /* 댓글 개수 */
    int getCount(Long boardId);

    default Comment dtoToEntity(CommentDTO commentDTO){

        return Comment.builder()
                .id(commentDTO.getId())
                .content(commentDTO.getContent())
                .createdAt(commentDTO.getCreatedAt())
                .upperId(commentDTO.getUpperId())
                .updatedAt(commentDTO.getUpdatedAt())
                .memberId(commentDTO.getMemberId())
                .boardId(commentDTO.getBoardId())
                .build();
    }

}
