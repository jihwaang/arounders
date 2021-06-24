package com.arounders.web.dto;


import com.arounders.web.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {

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


}
