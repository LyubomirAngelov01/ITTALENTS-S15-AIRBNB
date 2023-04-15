package com.finalproject.airbnb.model.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SimpleMessageDTO {
    private String recipient;
    private String subject;
    private String content;

}
