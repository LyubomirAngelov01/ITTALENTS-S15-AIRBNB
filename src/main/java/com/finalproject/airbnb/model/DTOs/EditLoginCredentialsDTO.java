package com.finalproject.airbnb.model.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditLoginCredentialsDTO {
    @Email
    @NotBlank
    private String email;
    private String password;
    @Pattern(regexp = "^(?=.*d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^_*-]).{8,}$", message = "Weak password")
    @NotBlank
    private String newPassword;
    private String confirmNewPassword;
}
