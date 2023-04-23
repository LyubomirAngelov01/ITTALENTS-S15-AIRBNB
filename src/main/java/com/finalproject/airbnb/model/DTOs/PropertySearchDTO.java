package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PropertySearchDTO {

    private String city;

    private Integer maxGuests;

    private Double price;

    private Integer bathrooms;

    private Integer bedrooms;

    private Integer beds;

    private Integer categoryNum;

}
