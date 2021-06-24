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

    public Integer getCode(){
        return this.code;
    }

    City(Integer code) { this.code = code; }

}

/*public enum City {

    SEOU(1, "서울특별시"),
    BUSAN(2, "부산광역시"),
    DAEGU(3, "대구광역시"),
    INCHEON(4, "인천광역시"),
    GWANGJU(5, "광주광역시"),
    DAEJEON(6, "대전광역시"),
    USAN(7, "울산광역시"),
    SEJONG(8, "세종특별자치시"),
    GYEONGGI(9, "경기도"),
    GANGWON(10, "강원도"),
    CHUNGBUK(11, "충청북도"),
    CHUNGNAM(12, "충청남도"),
    JEONBUK(13, "전라북도"),
    JEONNAM(14, "전라남도"),
    GYEONGBUK(15, "경상북도"),
    GYEONGNAM(16, "경상남도"),
    JEJU(17, "제주특별자치도")
    ;

    private Integer id;
    private String name;

    City(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Integer getIdx(String name){

        for(City city : values())
            if(name.equals(city.getName()))
                return city.getId();

        return null;
    }
}*/
