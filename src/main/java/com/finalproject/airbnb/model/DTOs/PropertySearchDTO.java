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

    private String streetAddress;

    private Integer maxGuests;

    private Double price;

    private Integer bathrooms;

    private Integer bedrooms;

    private Integer beds;

    private Integer categoryNum;

}
