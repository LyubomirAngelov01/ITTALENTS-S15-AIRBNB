package com.finalproject.airbnb.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistPropertyDTO {

    private String title;
    private List<String> photosUrl;
    private double avgRating;
    private double price;

}
