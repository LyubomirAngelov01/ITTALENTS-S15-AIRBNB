package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "country_codes")
public class CountryCode extends BaseEntity{


    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
