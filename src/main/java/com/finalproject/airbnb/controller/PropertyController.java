package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.service.PropertyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
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
    public DeletedPropertyDTO deleteProperty(@PathVariable int id, HttpSession s) {
        return propertyService.deleteProperty(id, getLoggedId(s));
    }

    @GetMapping("/properties/{id}/reviews/{page}")
    public List<ReviewInfoDTO> checkReviews(@PathVariable int id, @PathVariable int page) {
        return propertyService.checkReviews(id, page);
    }

    @PostMapping("/properties/all/{page}")
    public List<PropertyViewDTO> search(@Valid @RequestBody PropertySearchDTO dto, @PathVariable int page) {
        return propertyService.search(dto,  page);
    }
}
