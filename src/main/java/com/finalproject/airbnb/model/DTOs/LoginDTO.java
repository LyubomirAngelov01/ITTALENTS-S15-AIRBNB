package com.finalproject.airbnb.model.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
