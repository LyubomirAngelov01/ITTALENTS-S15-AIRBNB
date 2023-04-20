package com.finalproject.airbnb.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "reviews")
public class ReviewEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @Column
    private String comment;

    @Column
    private double rating;
}
