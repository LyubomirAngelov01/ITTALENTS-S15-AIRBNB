package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "amenities")
public class AmenitiesEntity extends BaseEntity{


    @OneToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @Column
    private boolean wifiConnection;

    @Column
    private boolean pool;

    @Column
    private boolean tv;

    @Column
    private boolean parking;

    @Column
    private boolean airConditioning;

    @Column
    private boolean barbecue;

    @Column
    private boolean smokeAlarm;

    @Column
    private boolean firstAid;

    @Column
    private boolean fireExtinguisher;

    @Column
    private boolean gym;

    @Column
    private boolean washer;

    @Column
    private boolean kitchen;
}
