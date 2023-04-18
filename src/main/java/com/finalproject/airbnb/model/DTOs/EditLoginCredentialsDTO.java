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

    @NotBlank
    private String password;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$&^*])(?=.*[0-9])(?=.*[a-z]).{8,}$", message = "Weak password, the password must include at least 1 uppercase character" +
            ", 1 lowercase character, 1 special symbol of (!,@,#,$,&,^,*), 1 number from 0 to 9 and at least 8 total symbols!")
    private String newPassword;
    @NotBlank
    private String confirmNewPassword;
}
