package com.finalproject.airbnb.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageWithUserDTO {
    private int senderId;
    private String senderFirstName;
    private String senderLastName;
    private String message;
    private LocalDateTime timeSent;

}
