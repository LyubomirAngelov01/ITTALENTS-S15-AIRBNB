package com.finalproject.airbnb.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reservations")
public class ReservationEntity extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;
    @Column
    private int guests;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

}
