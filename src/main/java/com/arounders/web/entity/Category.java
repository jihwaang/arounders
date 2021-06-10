package com.arounders.web.entity;


import lombok.Data;

public enum Category {
    ALL(1, "전체"),
    SHARE(2, "공유"),
    RECRUIT(3, "모집"),
    RESTAURANT(4, "맛집"),
    DAILY_COMMUNITY(5, "일상/소통"),
    QNA(6, "질문/답변");

    private Integer id;
    private String title;

    Category(Integer id, String title) {
        this.id = id;
        this.title = title;
    }
}
