package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.service.PropertyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "JWT token")
public class PropertyController extends AbstractController {

    @Autowired
    private PropertyService propertyService;


    @PostMapping("/properties")
    public PropertyViewDTO createProperty(@Valid @RequestBody PropertyInfoDTO dto, HttpServletRequest s) {
        int userId = extractUserIdFromToken(s);
        return propertyService.createProperty(dto, userId);
    }


    @PutMapping("/properties/{id}")
    public PropertyViewDTO editProperty(@PathVariable int id, @Valid @RequestBody PropertyInfoDTO dto, HttpServletRequest s) {
        int userId = extractUserIdFromToken(s);
        return propertyService.editProperty(id, dto, userId);
    }

    @GetMapping("/properties/{id}")
    public PropertyViewDTO showProperty(@PathVariable int id) {
        return propertyService.showProperty(id);
    }

    @DeleteMapping("/properties/{id}")
    public DeletedPropertyDTO deleteProperty(@PathVariable int id, HttpServletRequest s) {
        int userId = extractUserIdFromToken(s);
        return propertyService.deleteProperty(id, userId);
    }

    @GetMapping("/properties/{id}/reviews/{page}")
    public List<ReviewInfoDTO> checkReviews(@PathVariable int id, @PathVariable int page) {
        return propertyService.checkReviews(id, page);
    }

    @PostMapping("/properties/all/{page}")
    public List<PropertyViewDTO> search(@Valid @RequestBody PropertySearchDTO dto, @PathVariable int page) {
        return propertyService.search(dto, page);
    }
}
