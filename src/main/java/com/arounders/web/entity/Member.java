package com.arounders.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private String addr;
    private String addrDtl;
    private LocalDateTime createdAt;
    private LocalDateTime droppedAt;
    private LocalDateTime lastLoginAt;

    private Long roleId;
    private Integer cityId;

    public void generateNewPassword() {
        String newPassword = UUID.randomUUID().toString();
        this.password = newPassword.substring(newPassword.lastIndexOf("-")+1, newPassword.length());
    }

    public void setCityId() {
        this.cityId = City.valueOf(this.addr.split(" ")[0]).getCode();
    }
}
