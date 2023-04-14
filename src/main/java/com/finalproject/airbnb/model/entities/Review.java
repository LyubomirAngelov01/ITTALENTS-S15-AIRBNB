package com.finalproject.airbnb.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "reviews")
public class Review {

    @Id
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Id
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column
    private String comment;

    @Column
    private int rating;
}
