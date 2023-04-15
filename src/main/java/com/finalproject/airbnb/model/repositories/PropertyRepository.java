package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    Optional<Property> getById(int id);


    default boolean userOwnsProperty(int userId, int propertyId) {
        Optional<Property> optionalProperty = findById(propertyId);
        if (optionalProperty.isPresent()) {
            Property property = optionalProperty.get();
            User owner = property.getOwner();
            if (owner != null && owner.getId() == userId) {
                return true;
            }
        }
        return false;
    }
}
