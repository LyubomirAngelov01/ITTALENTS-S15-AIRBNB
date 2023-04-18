package com.finalproject.airbnb.model.DTOs;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {
    private LocalDate checkInDate;
    
    private LocalDate checkOutDate;

    @Min(value = 1)
    private int guests;

}
