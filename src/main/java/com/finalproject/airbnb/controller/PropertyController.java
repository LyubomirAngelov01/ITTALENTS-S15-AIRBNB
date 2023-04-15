package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.PropertyInfoDTO;
import com.finalproject.airbnb.model.DTOs.PropertySearchDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.service.PropertyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PropertyController extends AbstractController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/properties")
    public PropertyInfoDTO createProperty( @RequestPart("files") MultipartFile[] files, @RequestBody PropertyInfoDTO dto,HttpSession s){
        return propertyService.createProperty(dto, files,  getLoggedId(s));
    }

    @PutMapping("/properties/{id}")
    public PropertyInfoDTO editProperty(@PathVariable int id, @RequestBody PropertyInfoDTO dto
            , @RequestParam("files") MultipartFile[] files, HttpSession s) {
        return propertyService.editProperty(id, dto, files, getLoggedId(s));
    }

    @GetMapping("/properties/{id}")
    public PropertyInfoDTO showProperty(@PathVariable int id){
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
//    public void addToWishlist(@PathVariable int id, HttpSession s) {
//        return propertyService.addToWishlist(id, getLoggedId(s));
//    }

    @PostMapping("/properties/all")
    public List<PropertySearchDTO> search(@PathVariable int id, @RequestBody PropertySearchDTO dto) {
        return propertyService.search(dto);
    }
}
