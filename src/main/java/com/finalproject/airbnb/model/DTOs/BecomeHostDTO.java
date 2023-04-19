package com.finalproject.airbnb.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BecomeHostDTO {
    private int id;
    private String firstName;
    private String lastName;
    private boolean isHost;
}
