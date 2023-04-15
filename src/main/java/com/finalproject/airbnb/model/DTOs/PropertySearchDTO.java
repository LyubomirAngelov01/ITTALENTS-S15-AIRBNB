package com.finalproject.airbnb.model.DTOs;

import com.finalproject.airbnb.model.entities.Address;
import com.finalproject.airbnb.model.entities.Amenities;
import com.finalproject.airbnb.model.entities.Category;
import com.finalproject.airbnb.model.entities.PropertyType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PropertySearchDTO {

    private Address address;

    private int beds;

    private int maxGuests;

    private double price;

    private int bathrooms;

    private int bedrooms;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "property")
    private Amenities amenities;
}
