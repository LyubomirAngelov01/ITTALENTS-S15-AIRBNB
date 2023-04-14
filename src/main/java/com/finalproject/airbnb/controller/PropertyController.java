package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.PropertyInfoDTO;
import com.finalproject.airbnb.model.DTOs.PropertySearchDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropertyController extends AbstractController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/properties")
    public PropertyInfoDTO createProperty(@RequestBody PropertyInfoDTO dto){
        return propertyService.createProperty;
    }

    @PutMapping("/properties/{id}")
    public PropertyInfoDTO editProperty(@PathVariable int id, @RequestBody PropertyInfoDTO dto) {
        return propertyService.editProperty;
    }

    @GetMapping("/properties/{id}")
    public PropertyInfoDTO showProperty(@PathVariable int id) {
        return propertyService.showProperty;
    }

    @DeleteMapping("/properties/{id}")
    public void deleteProperty(@PathVariable int id) {
        return propertyService.deleteProperty;
    }

    @GetMapping("/properties/{id}/reviews")
    public ReviewInfoDTO checkReviews(@PathVariable int id) {
        return propertyService.checkReviews;
    }

    @PostMapping("/properties/{id}/wishlist")
    public void addToWishlist(@PathVariable int id) {
        return propertyService.addToWishlist;
    }

    @PostMapping("/properties/{id}/all")
    public PropertySearchDTO search(@PathVariable int id, @RequestBody PropertySearchDTO) {
        return propertyService.search;
    }

}
