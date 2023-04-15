package com.finalproject.airbnb.service;


import com.finalproject.airbnb.model.DTOs.DeletedPropertyDTO;
import com.finalproject.airbnb.model.DTOs.PropertyInfoDTO;
import com.finalproject.airbnb.model.DTOs.PropertySearchDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.model.entities.Photos;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService extends AbstractService {


    public PropertyInfoDTO createProperty(PropertyInfoDTO dto, MultipartFile[] files, int loggedId) {
        User u = getUserById(loggedId);
        Property property = propertyValidation(u, dto);
        uploadPhotos(property, files);
        property.setOwner(u);
        propertyRepository.save(property);
        return mapper.map(property, PropertyInfoDTO.class);
    }

    public PropertyInfoDTO editProperty(int id, PropertyInfoDTO dto,MultipartFile[] files, int loggedId) {
        User u = getUserById(loggedId);
        if(!propertyRepository.findById(id).isPresent()){
            throw new NotFoundException("Property not found!");
        }
        Property property = propertyValidation(u, dto);
        uploadPhotos(property, files);
        property.setOwner(u);
        propertyRepository.save(property);
        return mapper.map(property, PropertyInfoDTO.class);
    }

    public PropertyInfoDTO showProperty(int id){
        Property property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property not found!"));
        PropertyInfoDTO dto = mapper.map(property, PropertyInfoDTO.class);
        return dto;
    }

    public DeletedPropertyDTO deleteProperty (int id, int loggedId){
        User u = getUserById(loggedId);
        if (!u.isHost()) {
            throw new BadRequestException("You are not a host");
        }
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
                .filter(p -> p.getAddress().getStreetAddress().equals(dto.getAddress().getStreetAddress()))
                .filter(p -> p.getAmenities().equals(dto.getAmenities()))
                .filter(p -> p.getMaxGuests() < dto.getMaxGuests())
                .filter(p -> p.getCategory().equals(dto.getCategory()))
                .map(p -> mapper.map(p, PropertySearchDTO.class))
                .collect(Collectors.toList());
    }

    private Property propertyValidation(User u, PropertyInfoDTO dto){
        if (!u.isHost()) {
            throw new BadRequestException("You are not a host");
        }
        Property property = mapper.map(dto, Property.class);
        int countryId = property.getAddress().getCountry().getId();
        if (countryRepository.findById(countryId) == null) {
            throw new BadRequestException("Invalid country");
        }
       // property.getAddress().getCountry().setId(countryId);
        if (property.getBedrooms() > 50) {
            throw new BadRequestException("Maximum of 50 bedrooms");
        }
        if (property.getBathrooms() > 50){
            throw new BadRequestException("Maximum of 50 bathrooms");
        }
        if (property.getBeds() > 50) {
            throw new BadRequestException("Maximum of 50 beds");
        }
        if(property.getTitle().length() > 32) {
            throw new BadRequestException("Maximum title length of 32 characters!");
        }
        if(property.getDescription().length() > 500) {
            throw new BadRequestException("Maximum description length of 500 characters!");
        }
        if(property.getPrice()< 18 || property.getPrice() > 17729) {
            throw new BadRequestException("Price must be between 18 and 17 729 leva!");
        }

        return property;
    }

    @SneakyThrows
    protected Property uploadPhotos(Property property, MultipartFile[] files){
        for (MultipartFile file : files){
            Photos photo = new Photos();
            String name = UUID.randomUUID().toString();
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());

            name = name + "." + ext;
            File dir = new File("uploads");
            if(!dir.exists()){
                dir.mkdirs();
            }
            File f = new File(dir, name);
            Files.copy(file.getInputStream(), f.toPath());
            String path = dir.getName() + File.separator + f.getName();
            photo.setPhotoUrl(path);
            property.getPhotos().add(photo);
        }
        if(property.getPhotos().size() < 5) {
            throw new BadRequestException("Minimum of 5 photos!");
        }
        return property;
    }
}




