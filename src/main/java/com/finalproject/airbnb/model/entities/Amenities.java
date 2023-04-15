package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "amenities")
public class Amenities extends BaseEntity{



    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column
    @NotBlank
    private boolean wifi;

    @Column
    @NotBlank
    private boolean pool;

    @Column
    @NotBlank
    private boolean tv;

    @Column
    @NotBlank
    private boolean parking;

    @Column
    @NotBlank
    private boolean airConditioning;

    @Column
    @NotBlank
    private boolean barbecue;

    @Column
    @NotBlank
    private boolean smokeAlarm;

    @Column
    @NotBlank
    private boolean firstAid;

    @Column
    @NotBlank
    private boolean fireExtinguisher;

    @Column
    @NotBlank
    private boolean gym;

    @Column
    @NotBlank
    private boolean washer;

    @Column
    @NotBlank
    private boolean kitchen;
}
