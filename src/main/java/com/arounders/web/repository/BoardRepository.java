package com.arounders.web.repository;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import com.arounders.web.entity.Board;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    List<BoardDTO> getHiddenList();

    List<BoardDTO> getList(@Param("memberId") Long memberId, @Param("cri") BoardCriteria criteria);

    BoardDTO getBoard(Long id);

    int insert(Board board);

    int update(Board board);

    int delete(Long id);

    /* 게시글 숨기기 */
    int hide(Long id);

    /* 게시글 숨김 해제 */
    int show(Long id);

    /* 게시글 진행중 -> 종료 */
    int done(Long boardId);

    /* About Mypage  */
    List<BoardDTO> getMyList(@Param("cri") BoardCriteria criteria, Long memberId);

    Integer getCountByCategory(@Param("memberId") Long memberId, @Param("categoryId") Integer categoryId);
}
