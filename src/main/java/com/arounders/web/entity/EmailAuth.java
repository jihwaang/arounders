package com.arounders.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailAuth {

    private Long id;
    private String email;
    private boolean isConfirmed;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
    private String confirmKey;

}
