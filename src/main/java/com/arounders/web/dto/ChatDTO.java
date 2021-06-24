package com.arounders.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatDTO {

    private Long chatRoomId;
    private String region;

    private Long memberId;
    private String writer;
    private String message;

    private String time;

}
