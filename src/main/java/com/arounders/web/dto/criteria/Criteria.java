package com.arounders.web.dto.criteria;

import lombok.Data;

@Data
abstract class Criteria {

    private int page;
    private int size = 10;
    private int offset;

    public void init() {
        offset = (page - 1) * size;
    }
}
