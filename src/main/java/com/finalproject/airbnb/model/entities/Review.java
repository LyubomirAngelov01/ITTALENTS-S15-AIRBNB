package com.finalproject.airbnb.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "reviews")
public class Review extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column
    private String comment;

    @Column
    private double rating;
}
