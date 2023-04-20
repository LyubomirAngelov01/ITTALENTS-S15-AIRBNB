package com.finalproject.airbnb.model.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "messages")
public class MessageEntity extends BaseEntity {

    @Column
    private String message;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;


    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    @Column(name = "time_sent")
    private LocalDateTime timeSent;
}
