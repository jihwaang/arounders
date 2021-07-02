package com.arounders.web.dto;

import lombok.Data;

@Data
public class ChatMemberDTO {

    private Long id;
    private String nickname;

    private String uuid;
    private String path;
    private String name;

}
