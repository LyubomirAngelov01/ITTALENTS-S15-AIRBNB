package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Photos;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotosRepository extends JpaRepository<Photos, Integer> {

    void deleteAllByPropertyId(int id);

    List<Photos> findAllByPropertyId(int id);

    List<Photos> findByProperty(Property property);

    List<Photos> findAllById(int id);

    void deleteAllById(int id);

    default boolean propertyOwnsPhoto(int propertyId, int photoId){
        Optional<Photos> optionalPhotos = findById(photoId);
        if (optionalPhotos.isPresent()) {
            Photos photos = optionalPhotos.get();
            Property property = photos.getProperty();
            if (property != null && property.getId() == propertyId) {
                return true;
            }
        }
        return false;
    }
    }
