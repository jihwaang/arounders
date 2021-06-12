package com.arounders.web.entity;

public enum Role {

    ADMIN(0),
    USER(1);

    public Integer code;

    Role(Integer code) {
        this.code = code;
    }

}
