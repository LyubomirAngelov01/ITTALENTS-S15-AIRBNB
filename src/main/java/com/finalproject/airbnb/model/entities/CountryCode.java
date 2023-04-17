package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "country_codes")
public class CountryCode extends BaseEntity{



    @Column(name = "code")
    private String code;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
