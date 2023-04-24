package com.finalproject.airbnb.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpcomingReservationClientDTO {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int propertyId;
    private String title;
}
