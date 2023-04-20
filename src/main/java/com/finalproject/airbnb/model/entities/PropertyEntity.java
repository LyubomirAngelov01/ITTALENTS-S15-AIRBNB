package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "properties")
public class PropertyEntity extends BaseEntity{


    @Column
    private String streetAddress;

    @Column
    private String zipCode;

    @Column
    private String region;

    @Column
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

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
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "rating")
    private double avgRating;

    @OneToOne(mappedBy = "property")
    private AmenitiesEntity amenities;

    @OneToMany(mappedBy = "property")
    private List<PhotosEntity> photos;

    @OneToMany(mappedBy = "property")
    private List<ReviewEntity> reviews;
}
