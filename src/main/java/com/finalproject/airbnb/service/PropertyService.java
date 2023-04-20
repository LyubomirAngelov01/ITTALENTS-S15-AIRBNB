package com.finalproject.airbnb.service;


import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.model.entities.*;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService extends AbstractService {

    private static final Logger logger = LogManager.getLogger(PropertyService.class);


    @Transactional
    public PropertyViewDTO createProperty(PropertyInfoDTO dto, int loggedId) {
        UserEntity u = getUserById(loggedId);
        if (!u.isHost()) {
            throw new UnauthorizedException("You must be a host to list a property!");
        }
        PropertyEntity property = mapper.map(dto, PropertyEntity.class);
        CategoryEntity category = categoryRepository.findById(dto.getCategoryNum())
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        CountryEntity country = countryRepository.findById(dto.getCountryNum())
                .orElseThrow(() -> new NotFoundException("Country not found!"));
        AmenitiesEntity amenities = mapper.map(dto, AmenitiesEntity.class);
        amenities.setProperty(property);
        property.setCountry(country);
        property.setCategory(category);
        property.setOwner(u);
        propertyRepository.save(property);
        amenitiesRepository.save(amenities);
        countryRepository.save(country);
        categoryRepository.save(category);
        PropertyViewDTO propertyViewDTO = mapper.map(dto, PropertyViewDTO.class);
        propertyViewDTO.setCategoryName(property.getCategory().getCategoryName());
        propertyViewDTO.setCountryName(property.getCountry().getCountryName());
        logger.info("Created property" + LocalDate.now());
        return propertyViewDTO;
    }

    public PropertyViewDTO editProperty(int id, PropertyInfoDTO dto, int loggedId) {
        UserEntity u = getUserById(loggedId);
        PropertyEntity property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        if (!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("User does not own the property!");
        }
        CategoryEntity category = categoryRepository.findById(dto.getCategoryNum())
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        CountryEntity country = countryRepository.findById(dto.getCountryNum())
                .orElseThrow(() -> new NotFoundException("Country not found!"));
        AmenitiesEntity amenities = amenitiesRepository.getByProperty(property);
        mapper.map(dto, amenities);
        property.setCountry(country);
        property.setCategory(category);
        amenitiesRepository.save(amenities);
        countryRepository.save(country);
        categoryRepository.save(category);
        mapper.map(dto, property);
        propertyRepository.save(property);
        PropertyViewDTO propertyViewDTO = mapper.map(dto, PropertyViewDTO.class);
        propertyViewDTO.setCategoryName(property.getCategory().getCategoryName());
        propertyViewDTO.setCountryName(property.getCountry().getCountryName());
        //logger.info("Property created");
        return propertyViewDTO;
    }

    public PropertyViewDTO showProperty(int id) {
        PropertyEntity property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        PropertyViewDTO dto = mapper.map(property, PropertyViewDTO.class);
        dto.setCategoryName(property.getCategory().getCategoryName());
        dto.setCountryName(property.getCountry().getCountryName());
        return dto;
    }

    public DeletedPropertyDTO deleteProperty(int id, int loggedId) {
        UserEntity u = getUserById(loggedId);
        PropertyEntity property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        if (!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("Property is not owned by the user!");
        }
        propertyRepository.delete(property);
        return new DeletedPropertyDTO();
    }

    public List<ReviewInfoDTO> checkReviews(int id) {
        PropertyEntity property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found"));
        if (reviewRepository.findAllByProperty(property).isEmpty()) {
            throw new NotFoundException("No reviews found!");
        }
        return reviewRepository.findAllByProperty(property)
                .stream()
                .map(r -> mapper.map(r, ReviewInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<PropertyViewDTO> search(PropertySearchDTO dto) {
        List<PropertyViewDTO> properties = propertyRepository.findAll()
                .stream()
                .filter(p -> p.getPrice() <= dto.getPrice())
                .filter(p -> p.getBeds() >= dto.getBeds())
                .filter(p -> p.getBedrooms() >= dto.getBedrooms())
                .filter(p -> p.getBathrooms() >= dto.getBathrooms())
                .filter(p -> p.getStreetAddress().equals(dto.getStreetAddress()))
                .filter(p -> p.getMaxGuests() >= dto.getMaxGuests())
                .filter(p -> p.getCategory().getId() == dto.getCategoryNum())
                .map(p -> mapper.map(p, PropertyViewDTO.class))
                .collect(Collectors.toList());
        if (properties.isEmpty()) {
            throw new NotFoundException("Property not found!");
        }
        return properties;
    }
}




