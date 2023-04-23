package com.finalproject.airbnb.model.DTOs;

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

    @Pattern(regexp = "^[A-Z][a-z]{1,29}([-'][A-Z][a-z]{1,29})?$",message = "enter a valid first name")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-z]{1,29}([-'][A-Z][a-z]{1,29})?$",message = "enter a valid first name")
    private String lastName;

    private LocalDate birthdate;
    @Email(message = "invalid email, email should be structured as shown by this example: example@mailexample.com")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$&^*])(?=.*[0-9])(?=.*[a-z]).{8,}$", message = "Weak password, the password must include at least 1 uppercase character" +
            ", 1 lowercase character, 1 special symbol of (!,@,#,$,&,^,*), 1 number from 0 to 9 and at least 8 total symbols!")
    private String password;

    private String confirmPassword;

    private int countryCode;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$",message = "invalid phone number")
    private String phoneNumber;
}
