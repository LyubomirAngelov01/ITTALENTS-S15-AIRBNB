package com.finalproject.airbnb.model.DTOs;

import com.finalproject.airbnb.model.entities.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int propertyId;
    private String title;



}
