package com.arounders.web.service;

import com.arounders.web.entity.Interests;

public interface InterestsService {
    int getCountOfMember(Integer memberId);

    int getCountOfBoard(Integer boardId);

    int toggleInterests(Interests interests);

    int cancelInterests(Interests interests);
}
