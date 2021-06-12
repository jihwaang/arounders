package com.arounders.web.service;

import com.arounders.web.entity.Likes;

public interface LikesService {

    /* 좋아요 등록 */
    Long like(Likes likes);
    /* 좋아요 취소 */
    Long cancel(Likes likes);
    /* 특정 게시글의 좋아요 개수 */
    int getCount(Long boardId);
}
