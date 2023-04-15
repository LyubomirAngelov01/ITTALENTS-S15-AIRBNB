package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "countries")
public class Country extends BaseEntity{




    @Column
    private String countryName;

    @OneToMany(mappedBy = "country")
    private List<Address> addresses;
}
