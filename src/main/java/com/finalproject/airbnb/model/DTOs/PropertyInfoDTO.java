package com.finalproject.airbnb.model.DTOs;


import com.finalproject.airbnb.model.entities.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PropertyInfoDTO {

    @NotBlank(message = "Write down a street address!")
    private String streetAddress;

    @NotBlank(message = "Write down a region!")
    private String region;

    @NotBlank(message = "Write down a zip code!")
    private String zipCode;

    @NotBlank(message = "Write down a city!")
    private String city;

    @Range(min = 0, max = 196)
    private int countryNum;

    @Range(min = 0, max = 50)
    private int beds;

    @Range(min = 1)
    private int maxGuests;

    @Range(min = 18, max = 17729)
    private double price;

    @Range(min = 0, max = 50)
    private int bathrooms;

    @Range(min = 0, max = 50)
    private int bedrooms;

    @NotBlank
    @Length(max = 500)
    private String description;

    @NotBlank
    @Length(max = 32)
    private String title;

    @Range(min = 0, max = 3)
    private int categoryNum;

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
