package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column
    private String streetAddress;

    @Column
    private String zipCode;

    @Column
    private String region;

    @Column
    private String city;

    @OneToMany(mappedBy = "address")
    private List<Property> property;
}
