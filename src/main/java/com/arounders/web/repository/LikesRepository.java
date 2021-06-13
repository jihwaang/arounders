package com.arounders.web.repository;

import com.arounders.web.entity.Likes;

public interface LikesRepository {

    /* 좋아요 여부 */
    int getLikesByMemberIdAndBoardId(Long memberId, Long boardId);
    /* 좋아요 등록 */
    int insert(Likes likes);
    /* 좋아요 취소 */
    int delete(Likes likes);
    /* 특정 게시글의 좋아요 개수 */
    int getCount(Long boardId);
}
