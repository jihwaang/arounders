package com.arounders.web.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
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
}
