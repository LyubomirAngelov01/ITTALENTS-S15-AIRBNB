package com.finalproject.airbnb.model.DTOs;

import com.finalproject.airbnb.model.entities.Property;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class SuccessfulReservationDTO {
    private int propertyId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;


}
