package com.finalproject.airbnb.service;


import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.model.entities.*;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService extends AbstractService {


    @Transactional
    public PropertyViewDTO createProperty(PropertyInfoDTO dto, int loggedId) {
        User u = getUserById(loggedId);
        if (!u.isHost()) {
            throw new UnauthorizedException("You must be a host to list a property!");
        }
        Property property = mapper.map(dto, Property.class);
        Category category = categoryRepository.findById(dto.getCategoryNum())
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        Country country = countryRepository.findById(dto.getCountryNum())
                .orElseThrow(() -> new NotFoundException("Country not found!"));
        Amenities amenities = mapper.map(dto, Amenities.class);
        amenities.setProperty(property);
        property.setCountry(country);
        property.setCategory(category);
        property.setOwner(u);
        propertyRepository.save(property);
        amenitiesRepository.save(amenities);
        countryRepository.save(country);
        categoryRepository.save(category);
        propertyRepository.save(property);
        PropertyViewDTO propertyViewDTO = mapper.map(property, PropertyViewDTO.class);
        propertyViewDTO.setCategoryName(property.getCategory().getCategoryName());
        propertyViewDTO.setCountryName(property.getCountry().getCountryName());
        return mapper.map(property, PropertyViewDTO.class);
    }

    public PropertyViewDTO editProperty(int id, PropertyInfoDTO dto, int loggedId) {
        User u = getUserById(loggedId);
        Property property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        if (!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("User does not own the property!");
        }
        Category category = categoryRepository.findById(dto.getCategoryNum())
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        Country country = countryRepository.findById(dto.getCountryNum())
                .orElseThrow(() -> new NotFoundException("Country not found!"));
        Amenities amenities = mapper.map(dto, Amenities.class);
        amenities.setProperty(property);
        property.setCountry(country);
        property.setCategory(category);
        amenitiesRepository.save(amenities);
        countryRepository.save(country);
        categoryRepository.save(category);
        mapper.map(dto, property);
        propertyRepository.save(property);
        PropertyViewDTO propertyViewDTO = mapper.map(property, PropertyViewDTO.class);
        propertyViewDTO.setCategoryName(property.getCategory().getCategoryName());
        propertyViewDTO.setCountryName(property.getCountry().getCountryName());
        return mapper.map(property, PropertyViewDTO.class);
    }

    public PropertyViewDTO showProperty(int id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        PropertyViewDTO dto = mapper.map(property, PropertyViewDTO.class);
        dto.setCategoryName(property.getCategory().getCategoryName());
        dto.setCountryName(property.getCountry().getCountryName());
        return dto;
    }

    public DeletedPropertyDTO deleteProperty(int id, int loggedId) {
        User u = getUserById(loggedId);
        Property property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        if (!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("Property is not owned by the user!");
        }
        propertyRepository.delete(property);
        return new DeletedPropertyDTO();
    }

    public List<ReviewInfoDTO> checkReviews(int id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found"));
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




