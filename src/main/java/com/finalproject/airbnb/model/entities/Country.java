package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "countries")
public class Country extends BaseEntity{



    @Column
    @NotBlank
    private String countryName;

    @OneToMany(mappedBy = "country")
    private List<Address> addresses;
}
