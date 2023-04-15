package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeletedPropertyDTO {

    private final String msg = "PROPERTY HAS BEEN DELETED";

}
