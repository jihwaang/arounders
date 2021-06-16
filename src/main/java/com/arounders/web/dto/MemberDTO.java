package com.arounders.web.dto;


import com.arounders.web.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {

    private String email;
    private String password;
    private String nickName;
    private String phone;
    private String addr;
    private String addrDtl;

}
