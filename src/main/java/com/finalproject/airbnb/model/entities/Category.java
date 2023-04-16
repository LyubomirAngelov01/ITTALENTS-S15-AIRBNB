package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "categories")
public class Category extends BaseEntity{


    @Column
    private String categoryName;

}
