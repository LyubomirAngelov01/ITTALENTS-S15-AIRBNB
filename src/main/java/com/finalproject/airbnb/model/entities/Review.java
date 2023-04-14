package com.finalproject.airbnb.model.entities;


import com.finalproject.airbnb.compositeKeysClasses.ReviewKeys;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "reviews")
@IdClass(ReviewKeys.class)
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
