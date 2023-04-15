package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.CreatePropertyDTO;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

//    @PostMapping("/properties")
//    public CreatePropertyDTO createProperty(CreatePropertyDTO dto){
////        return propertyService.createproperty;
//    }
}
