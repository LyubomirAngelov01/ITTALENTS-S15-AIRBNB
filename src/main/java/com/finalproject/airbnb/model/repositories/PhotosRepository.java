package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.PhotosEntity;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotosRepository extends JpaRepository<PhotosEntity, Integer> {

    void deleteAllByPropertyId(int id);

    List<PhotosEntity> findAllByPropertyId(int id);

    List<PhotosEntity> findByProperty(PropertyEntity property);

    List<PhotosEntity> findAllById(int id);

    void deleteAllById(int id);

    default boolean propertyOwnsPhoto(int propertyId, int photoId){
        Optional<PhotosEntity> optionalPhotos = findById(photoId);
        if (optionalPhotos.isPresent()) {
            PhotosEntity photos = optionalPhotos.get();
            PropertyEntity property = photos.getProperty();
            if (property != null && property.getId() == propertyId) {
                return true;
            }
        }
        return false;
    }
    }
