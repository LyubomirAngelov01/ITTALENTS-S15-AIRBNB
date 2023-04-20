package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Integer> {

    List<PropertyEntity> findAllByOwner(UserEntity owner);


    default boolean userOwnsProperty(int userId, int propertyId) {
        Optional<PropertyEntity> optionalProperty = findById(propertyId);
        if (optionalProperty.isPresent()) {
            PropertyEntity property = optionalProperty.get();
            UserEntity owner = property.getOwner();
            if (owner != null && owner.getId() == userId) {
                return true;
            }
        }
        return false;
    }
}
