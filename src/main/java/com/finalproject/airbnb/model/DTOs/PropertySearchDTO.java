package com.finalproject.airbnb.model.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
public class PropertySearchDTO {

    @NotBlank
    private String streetAddress;

    @NotBlank
    private int maxGuests;

    @NotBlank
    private double price;

    private int bathrooms;

    @Range(min = 0)
    private int bedrooms;

    @Range(min = 0, max = 50)
    private int beds;

    private int categoryNum;

}
