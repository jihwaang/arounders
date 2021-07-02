package com.arounders.web.repository;

import com.arounders.web.entity.Interests;

public interface InterestsRepository {
    int getCountOfMember(Long memberId);

    int getCountOfBoard(Long boardId);

    int insert(Interests interests);

    int delete(Interests interests);

    int getExsistingCount(Interests interests);
}
