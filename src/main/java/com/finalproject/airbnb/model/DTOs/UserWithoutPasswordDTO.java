package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWithoutPasswordDTO {

    private int id;
    public String firstName;
    public String lastName;

}
