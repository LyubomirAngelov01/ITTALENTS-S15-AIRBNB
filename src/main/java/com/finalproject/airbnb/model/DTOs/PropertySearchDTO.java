package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PropertySearchDTO {

    private String streetAddress;

    private int beds;

    private int maxGuests;

    private double price;

    private int bathrooms;

    private int bedrooms;

    private int categoryNum;

}
