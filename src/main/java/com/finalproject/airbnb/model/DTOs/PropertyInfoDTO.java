package com.finalproject.airbnb.model.DTOs;


import com.finalproject.airbnb.model.entities.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PropertyInfoDTO {

    private Address address;

    private int beds;

    private int maxGuests;

    private double price;

    private int bathrooms;

    private int bedrooms;

    private String description;

    private String title;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "property")
    private Amenities amenities;

    @OneToMany(mappedBy = "property")
    private List<Photos> photos;

}
