package com.arounders.web.entity;

public enum City {
    서울특별시(1),
    부산광역시(2),
    대구광역시(3),
    인천광역시(4),
    광주광역시(5),
    대전광역시(6),
    울산광역시(7),
    세종특별자치시(8),
    경기도(9),
    강원도(10),
    충청북도(11),
    충청남도(12),
    전라북도(13),
    전라남도(14),
    경상북도(15),
    경상남도(16),
    제주특별자치도(17)
    ;

    private Integer code;

    public Integer getCode() {
        return this.code;
    }

    City(Integer code) { this.code = code; }

}
