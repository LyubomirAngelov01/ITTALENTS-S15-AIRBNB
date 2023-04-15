package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "addresses")
public class Address extends BaseEntity{



    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column
    @NotBlank
    private String streetAddress;

    @Column
    @NotBlank
    private String zipCode;

    @Column
    @NotBlank
    private String region;

    @Column
    @NotBlank
    private String city;

    @OneToMany(mappedBy = "address")
    private List<Property> property;
}
