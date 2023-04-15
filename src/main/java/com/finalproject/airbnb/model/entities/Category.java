package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "categories")
public class Category extends BaseEntity{


    @Column
    private String category;

    @OneToMany(mappedBy = "category")
    private List<Property> properties;
}
