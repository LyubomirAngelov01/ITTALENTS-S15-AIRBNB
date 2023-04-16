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
        Property property = mapper.map(dto, Property.class);
        Category category = categoryRepository.findById(dto.getCategoryNum())
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        Country country = countryRepository.findById(dto.getCountryNum())
                .orElseThrow(() -> new NotFoundException("Country not found!"));
        Amenities amenities = setAmeneties(dto);
        amenitiesRepository.save(amenities);
        countryRepository.save(country);
        categoryRepository.save(category);
        amenities.setProperty(property);
        property.setCountry(country);
        property.setCategory(category);
//        photosRepository.saveAll(uploadPhotos(photos));
//        property.setPhotos(uploadPhotos(photos));
        property.setOwner(u);
        propertyRepository.save(property);
        PropertyViewDTO propertyViewDTO = mapper.map(property, PropertyViewDTO.class);
        propertyViewDTO.setCategoryName(property.getCategory().getCategoryName());
        propertyViewDTO.setCountryName(property.getCountry().getCountryName());
        return mapper.map(property, PropertyViewDTO.class);
    }

    public PropertyViewDTO editProperty(int id, PropertyInfoDTO dto, int loggedId) {
        User u = getUserById(loggedId);
        Property property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        if(!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("User does not own the property!");
        }
//        photosRepository.saveAll(uploadPhotos(photos));
//        property.setPhotos(uploadPhotos(photos));
        mapper.map(dto, property);
        propertyRepository.save(property);
        PropertyViewDTO propertyViewDTO = mapper.map(property, PropertyViewDTO.class);
        propertyViewDTO.setCategoryName(property.getCategory().getCategoryName());
        propertyViewDTO.setCountryName(property.getCountry().getCountryName());
        return mapper.map(property, PropertyViewDTO.class);
    }

    public PropertyViewDTO showProperty(int id){
        Property property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        PropertyViewDTO dto = mapper.map(property, PropertyViewDTO.class);
        dto.setCategoryName(property.getCategory().getCategoryName());
        dto.setCountryName(property.getCountry().getCountryName());
        return dto;
    }

    public DeletedPropertyDTO deleteProperty (int id, int loggedId){
        User u = getUserById(loggedId);
        Property property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        if(!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("Property is not owned by the user!");
        }
        propertyRepository.delete(property);
        return new DeletedPropertyDTO();
    }

    public List<ReviewInfoDTO> checkReviews(int id) {
        if(propertyRepository.findById(id).isPresent()){
            throw new NotFoundException("Property not found!");
        }
        return reviewRepository.findAll()
                .stream()
                .map(r -> mapper.map(r, ReviewInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<PropertySearchDTO> search(PropertySearchDTO dto) {
        return propertyRepository.findAll()
                .stream()
                .filter(p -> p.getPrice() == dto.getPrice())
                .filter(p -> p.getBeds() == dto.getBeds())
                .filter(p -> p.getBedrooms() == dto.getBedrooms())
                .filter(p -> p.getBathrooms() == dto.getBathrooms())
                .filter(p -> p.getStreetAddress().equals(dto.getStreetAddress()))
                .filter(p -> p.getMaxGuests() < dto.getMaxGuests())
                .filter(p -> p.getCategory().getId() == dto.getCategoryNum())
                .map(p -> mapper.map(p, PropertySearchDTO.class))
                .collect(Collectors.toList());
    }


//    @Transactional
//    private void propertyValidation(User u, PropertyInfoDTO dto){
//        if (!u.isHost()) {
//            throw new BadRequestException("You are not a host");
//        }
//        Property property = mapper.map(dto, Property.class);
//        if (property.getBedrooms() > 50 || property.getBedrooms() < 0) {
//            throw new BadRequestException("Range must be between 0 and 50 bedrooms");
//        }
//        if (property.getBathrooms() > 50 || property.getBathrooms() < 0){
//            throw new BadRequestException("Range must be between 0 and 50 bathrooms");
//        }
//        if (property.getBeds() > 50 || property.getBeds() < 0) {
//            throw new BadRequestException("Range must be between 0 and 50 beds");
//        }
//        if(property.getTitle().length() > 32 || property.getTitle().length() == 0) {
//            throw new BadRequestException("Length of title must be between 0 and 32 characters");
//        }
//        if(property.getDescription().length() > 500 || property.getDescription().length() == 0) {
//            throw new BadRequestException("Length of description must be between 0 and 500 characters!");
//        }
//        if(property.getPrice() < 18 || property.getPrice() > 17729) {
//            throw new BadRequestException("Price must be between 18 and 17 729 leva!");
//        }
//
//    }


    private Amenities setAmeneties(PropertyInfoDTO dto) {
        Amenities amenities = new Amenities();
        amenities.setGym(dto.isGym());
        amenities.setBarbecue(dto.isBarbecue());
        amenities.setKitchen(dto.isKitchen());
        amenities.setTv(dto.isTv());
        amenities.setPool(dto.isPool());
        amenities.setAirConditioning(dto.isAirConditioning());
        amenities.setFirstAid(dto.isFirstAid());
        amenities.setFireExtinguisher(dto.isFireExtinguisher());
        amenities.setParking(dto.isParking());
        amenities.setFirstAid(dto.isFirstAid());
        amenities.setWasher(dto.isWasher());
        return amenities;
    }

    @SneakyThrows
    public List<Photos> uploadPhotos (MultipartFile[] photos) {

        if (photos.length < 5) {
            throw  new BadRequestException("Minimum of 5 pictures!");
        }

            for (MultipartFile photo : photos) {
                String contentType = photo.getContentType();
                if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                    throw  new BadRequestException("Only JPEG and PNG files are allowed.");
                }
            }
            List<Photos> photoEntities = new ArrayList<>();
            for (MultipartFile photo : photos) {
                String ext = FilenameUtils.getExtension(photo.getOriginalFilename());
                String filename = UUID.randomUUID().toString() + ext;

                    Path filePath = Paths.get("path/to/photos/" + filename);
                    Files.createDirectories(filePath.getParent());
                    Files.write(filePath, photo.getBytes());
                Photos photo1 = new Photos();
                photo1.setPhotoUrl("/photos/" + filename);
                photoEntities.add(photo1);
            }
            return photoEntities;
    }


}




