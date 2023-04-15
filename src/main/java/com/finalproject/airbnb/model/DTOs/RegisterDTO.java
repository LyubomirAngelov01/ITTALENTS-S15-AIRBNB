package com.finalproject.airbnb.model.DTOs;

import com.finalproject.airbnb.model.entities.CountryCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String confirmPassword;
    private int countryCodeId;
    private String phoneNumber;


}
