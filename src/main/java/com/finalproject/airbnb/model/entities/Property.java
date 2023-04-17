package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Setter
@Entity(name = "properties")
public class Property extends BaseEntity{


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
    private Country country;

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
