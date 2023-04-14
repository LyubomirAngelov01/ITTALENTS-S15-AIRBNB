package com.finalproject.airbnb.model.DTOs;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {

    private String msg;
    private int status;
    private LocalDateTime time;
}
