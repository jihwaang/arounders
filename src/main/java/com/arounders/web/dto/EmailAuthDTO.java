package com.arounders.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailAuthDTO {

    private String email;
    private boolean isConfirmed;
    private String confirmKey;

}
