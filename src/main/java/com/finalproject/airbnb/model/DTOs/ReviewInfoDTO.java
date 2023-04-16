package com.finalproject.airbnb.model.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
public class ReviewInfoDTO {

    @NotBlank
    private String comment;

    @NotBlank
    @Length(max = 5)
    private double rating;
}
