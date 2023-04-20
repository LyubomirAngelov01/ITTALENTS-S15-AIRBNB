package com.finalproject.airbnb.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PropertyViewDTO {
    private String streetAddress;

    private String region;

    private String zipCode;

    private String city;

    private String countryName;

    private int beds;

    private int maxGuests;

    private double price;

    private int bathrooms;

    private int bedrooms;

    private String description;

    private String title;

    private String categoryName;

    private boolean wifiConnection;

    private boolean pool;
    private boolean tv;
    private boolean parking;
    private boolean airConditioning;
    private boolean barbecue;
    private boolean smokeAlarm;
    private boolean firstAid;
    private boolean fireExtinguisher;
    private boolean gym;
    private boolean washer;
    private boolean kitchen;
}
