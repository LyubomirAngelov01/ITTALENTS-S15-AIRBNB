package com.finalproject.airbnb.model.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SimpleMessageDTO {
    private String sendTo;
    private String subject;
    private String content;

}
