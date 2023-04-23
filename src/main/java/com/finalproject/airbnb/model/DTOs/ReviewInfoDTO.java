package com.finalproject.airbnb.model.DTOs;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfoDTO {

    @NotBlank
    private String comment;

    private Double rating;
}
