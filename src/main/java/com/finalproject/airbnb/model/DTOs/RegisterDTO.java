package com.finalproject.airbnb.model.DTOs;

import com.finalproject.airbnb.model.entities.CountryCode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "enter a valid first name")
    private String firstName;
    @NotBlank(message = "enter a valid last name")
    private String lastName;

    private LocalDate birthdate;
    @Email(message = "invalid email")
    private String email;
    @Pattern(regexp = "^(?=.*d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^_*-]).{8,}$", message = "Weak password")
    private String password;
    private String confirmPassword;
    private int countryCodeId;
    @NotBlank
    @Pattern(regexp = "^[0-9]+$",message = "invalid phone number")
    private String phoneNumber;


}
