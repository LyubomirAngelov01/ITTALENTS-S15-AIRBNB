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
    public String firstName;
    public String lastName;
    public LocalDate birthdate;
    public String email;
    public String password;
    public String confirmPassword;
    public int countryCodeId;
    public String phoneNumber;


}
