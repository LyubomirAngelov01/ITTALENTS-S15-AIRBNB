package com.finalproject.airbnb.model.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditProfileInfoDTO {
    @NotBlank(message = "enter a valid first name")
    private String firstName;
    @NotBlank(message = "enter a valid last name")
    private String lastName;
    @NotBlank
    @Pattern(regexp = "^[0-9]+$",message = "invalid phone number")
    private String phoneNumber;
    private int countryCode;
}
