package com.arounders.web.dto.criteria;

import lombok.Data;

@Data
public class BoardCriteria extends Criteria{

    private Integer cityId;
    private String region; //지역
    private String order = "desc";
    private Long category; //카테고리
    private String like; //좋아요 순
    private String status; //미완료/완료
    private String field;
    private String keyword;

}