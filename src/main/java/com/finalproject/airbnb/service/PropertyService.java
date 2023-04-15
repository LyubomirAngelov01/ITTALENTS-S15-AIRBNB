package com.finalproject.airbnb.service;


import com.finalproject.airbnb.model.DTOs.PropertyInfoDTO;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class PropertyService extends AbstractService{

    public PropertyInfoDTO createProperty(PropertyInfoDTO dto, int loggedId) {
        User u = getUserById(loggedId);
        if(!u.isHost()){
            throw new BadRequestException("You are not a host");
        }
        Property property = mapper.map(dto, Property.class);
       if(property.getAddress().getCity() == null || property.getAddress().getStreetAddress() == null ||
       property.getAddress().getZipCode() == null || property.getAddress().getRegion() == null)
x
    }
}
