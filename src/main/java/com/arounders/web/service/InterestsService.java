package com.arounders.web.service;

import com.arounders.web.entity.Interests;

public interface InterestsService {
    int getCountOfMember(Long memberId);

    int getCountOfBoard(Long boardId);

    int toggleInterests(Interests interests);

    int cancelInterests(Interests interests);
}
