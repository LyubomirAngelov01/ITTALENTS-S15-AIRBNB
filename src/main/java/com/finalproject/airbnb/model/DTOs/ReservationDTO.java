package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;
}
