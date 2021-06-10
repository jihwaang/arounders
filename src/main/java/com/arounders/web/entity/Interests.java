package com.arounders.web.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Interests {

    private Long memberId;
    private Long boardId;
}
