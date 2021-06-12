package com.arounders.web.repository;

import com.arounders.web.entity.Interests;

public interface InterestsRepository {
    int getCountOfMember(Integer memberId);

    int getCountOfBoard(Integer boardId);

    int insert(Interests interests);

    int delete(Interests interests);

    int getExsistingCount(Interests interests);
}
