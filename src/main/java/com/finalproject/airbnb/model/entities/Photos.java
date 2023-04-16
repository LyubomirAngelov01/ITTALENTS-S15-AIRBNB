package com.finalproject.airbnb.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "photos")
public class Photos extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "photo_url")
    private String photoUrl;
}
