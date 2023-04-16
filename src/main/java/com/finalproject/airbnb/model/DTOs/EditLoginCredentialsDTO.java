package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditLoginCredentialsDTO {
    private String email;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
}
