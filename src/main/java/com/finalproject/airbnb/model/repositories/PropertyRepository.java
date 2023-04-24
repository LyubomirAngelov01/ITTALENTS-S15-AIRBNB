package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Integer> {

    List<PropertyEntity> findAllByOwner(UserEntity owner);

    void deleteAllByOwner(UserEntity owner);


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

    @Query
            ("SELECT p FROM properties p WHERE "
                    + "(:city IS NULL OR p.city = :city) AND "
                    + "(:maxGuests IS NULL OR p.maxGuests >= :maxGuests) AND "
                    + "(:price IS NULL OR p.price <= :price) AND "
                    + "(:bathrooms IS NULL OR p.bathrooms >= :bathrooms) AND "
                    + "(:bedrooms IS NULL OR p.bedrooms >= :bedrooms) AND "
                    + "(:beds IS NULL OR p.beds >= :beds) AND "
                    + "(:categoryNum IS NULL OR p.category.id = :categoryNum)")
    Page<PropertyEntity> findBySearchParams(
            @Param("city") String city,
            @Param("maxGuests") Integer maxGuests,
            @Param("price") Double price,
            @Param("bathrooms") Integer bathrooms,
            @Param("bedrooms") Integer bedrooms,
            @Param("beds") Integer beds,
            @Param("categoryNum") Integer categoryNum,
            Pageable pageable);

}
