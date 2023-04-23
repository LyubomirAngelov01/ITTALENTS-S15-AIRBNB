package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ChatDTO {

    private String message;
    private LocalDateTime timeSent;
    private int senderId;
    private int receiverId;
}
