package com.finalproject.airbnb.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "photos")
public class PhotosEntity extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @Column(name = "photo_url")
    private String photoUrl;
}
