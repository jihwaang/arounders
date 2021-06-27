package com.arounders.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

    private Long id;
    private String title;
    private String region;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    private String creator;
    private String city;
    private Integer count;
    //private Set<WebSocketSession> sessions = new HashSet<>();

    private Long memberId;
    private Integer cityId;

//    private static OutputStream fos;

}
