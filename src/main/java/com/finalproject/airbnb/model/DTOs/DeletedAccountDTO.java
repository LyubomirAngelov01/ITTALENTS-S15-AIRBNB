package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeletedAccountDTO {

    private final String msg = "ACCOUNT HAS BEEN DELETED";
}
