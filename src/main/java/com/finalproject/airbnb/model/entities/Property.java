package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column
    private int beds;

    @Column
    private int maxGuests;

    @Column
    private double price;

    @Column
    private int bathrooms;

    @Column
    private int bedrooms;

    @Column
    private String description;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "rating")
    private double avgRating;

    @OneToOne(mappedBy = "property")
    private Amenities amenities;

    @OneToMany(mappedBy = "property")
    private List<Photos> photos;

    @OneToMany(mappedBy = "property")
    private List<Review> reviews;
}
