package com.finalproject.airbnb.service;


import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.finalproject.airbnb.model.DTOs.PropertyInfoDTO;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class PropertyService extends AbstractService {


    public PropertyInfoDTO createProperty(PropertyInfoDTO dto, MultipartFile[] files, int loggedId) {
        User u = getUserById(loggedId);
        if (!u.isHost()) {
            throw new BadRequestException("You are not a host");
        }
        Property property = mapper.map(dto, Property.class);
        int countryId = property.getAddress().getCountry().getId();
        if (countryRepository.findCountriesById(countryId) == false) {
            throw new BadRequestException("Invalid country");
        }
        property.getAddress().getCountry().setCountryName(countryRepository.setCountriesById(countryId));
        if (property.getBedrooms() > 50) {
            throw new BadRequestException("Maximum of 50 bedrooms");
        }
        if (property.getBathrooms() > 50){
            throw new BadRequestException("Maximum of 50 bathrooms");
        }
        if (property.getBeds() > 50) {
            throw new BadRequestException("Maximum of 50 beds");
        }
        uploadPhotos(property, files);
        if(property.getTitle().length() > 32) {
            throw new BadRequestException("Maximum title length of 32 characters!");
        }
        if(property.getDescription().length() > 500) {
            throw new BadRequestException("Maximum description length of 500 characters!");
        }
        if(property.getPrice()< 18 || property.getPrice() > 17729) {
            throw new BadRequestException("Price must be between 18 and 17 729 leva!");
        }
        property.setOwner(u);
        propertyRepository.save(property);
        return mapper.map(property, PropertyInfoDTO.class);
    }

    public PropertyInfoDTO editProperty(int id, PropertyInfoDTO dto,MultipartFile[] files, int loggedId) {
        User u = getUserById(loggedId);
        if (!u.isHost()) {
            throw new BadRequestException("You are not a host");
        }
        if(!propertyRepository.getById(id).isPresent()) {
            throw new NotFoundException("Property not found!");
        }
        Property property = mapper.map(dto, Property.class);
        if(!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("Property is not owned by the user!");
        }
        int countryId = property.getAddress().getCountry().getId();
        if (countryRepository.findCountriesById(countryId) == false) {
            throw new BadRequestException("Invalid country");
        }
        property.getAddress().getCountry().setCountryName(countryRepository.setCountriesById(countryId));
        if (property.getBedrooms() > 50) {
            throw new BadRequestException("Maximum of 50 bedrooms");
        }
        if (property.getBathrooms() > 50){
            throw new BadRequestException("Maximum of 50 bathrooms");
        }
        if (property.getBeds() > 50) {
            throw new BadRequestException("Maximum of 50 beds");
        }
        uploadPhotos(property, files);
        if(property.getTitle().length() > 32) {
            throw new BadRequestException("Maximum title length of 32 characters!");
        }
        if(property.getDescription().length() > 500) {
            throw new BadRequestException("Maximum description length of 500 characters!");
        }
        if(property.getPrice()< 18 || property.getPrice() > 17729) {
            throw new BadRequestException("Price must be between 18 and 17 729 leva!");
        }
        property.setOwner(u);
        propertyRepository.save(property);
        return mapper.map(property, PropertyInfoDTO.class);
    }

    public PropertyInfoDTO showProperty(int id){
        Optional<Property> property = propertyRepository.getById(id);
        if(!propertyRepository.getById(id).isPresent()) {
            throw new NotFoundException("Property not found!");
        }
        PropertyInfoDTO dto = mapper.map(property, PropertyInfoDTO.class);
        return dto;
    }

    public void deleteProperty (int id, ){
        User u = getUserById(loggedId);
        if (!u.isHost()) {
            throw new BadRequestException("You are not a host");
        }
        if(!propertyRepository.getById(id).isPresent()) {
            throw new NotFoundException("Property not found!");
        }
        Property property = mapper.map(dto, Property.class);
        if(!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("Property is not owned by the user!");
        }
    }
}




