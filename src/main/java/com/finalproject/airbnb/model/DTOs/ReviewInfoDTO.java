package com.finalproject.airbnb.model.DTOs;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
@NoArgsConstructor
public class ReviewInfoDTO {

    @NotBlank
    private String comment;

    private Double rating;
}
