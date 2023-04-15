package com.finalproject.airbnb.model.DTOs;

import com.finalproject.airbnb.model.entities.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditProfileInfoDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int countryCodeId;
}
