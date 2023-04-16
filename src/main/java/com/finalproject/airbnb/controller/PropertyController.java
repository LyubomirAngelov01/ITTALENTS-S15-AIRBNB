package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.PropertyInfoDTO;
import com.finalproject.airbnb.model.DTOs.PropertySearchDTO;
import com.finalproject.airbnb.model.DTOs.PropertyViewDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.service.PropertyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PropertyController extends AbstractController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/properties")
    public PropertyViewDTO createProperty(@Valid @RequestBody PropertyInfoDTO dto, HttpSession s){
        return propertyService.createProperty(dto, getLoggedId(s));
    }

    @PutMapping("/properties/{id}")
    public PropertyViewDTO editProperty(@PathVariable int id,@Valid @RequestBody PropertyInfoDTO dto, HttpSession s) {
        return propertyService.editProperty(id, dto, getLoggedId(s));
    }

    @GetMapping("/properties/{id}")
    public PropertyViewDTO showProperty(@PathVariable int id){
        return propertyService.showProperty(id);
    }

    @DeleteMapping("/properties/{id}")
    public void deleteProperty(@PathVariable int id, HttpSession s) {
        propertyService.deleteProperty(id, getLoggedId(s));
    }

    @GetMapping("/properties/{id}/reviews")
    public List<ReviewInfoDTO> checkReviews(@PathVariable int id) {
        return propertyService.checkReviews(id);
    }

//    @PostMapping("/properties/{id}/wishlist")
//    public void addToWishlist(@PathVariable int id) {
//        return propertyService.addToWishlist;
//    }

    @PostMapping("/properties/all")
    public List<PropertySearchDTO> search(@RequestBody PropertySearchDTO dto) {
        return propertyService.search(dto);
    }
}
