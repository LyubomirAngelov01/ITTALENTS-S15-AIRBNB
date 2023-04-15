package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "property_types")
public class PropertyType extends BaseEntity{




    @Column()
    private String type;

    @OneToMany(mappedBy = "propertyType")
    private List<Property> properties;
}