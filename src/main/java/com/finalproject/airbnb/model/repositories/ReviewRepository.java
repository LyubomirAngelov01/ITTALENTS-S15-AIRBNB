package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReviewEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

   List<ReviewEntity> findAllByProperty(PropertyEntity property, Pageable pageable);

   boolean existsByOwner(UserEntity owner);

   boolean existsByOwnerAndProperty(UserEntity owner, PropertyEntity property);


   @Query(value = "SELECT AVG(rating) FROM reviews WHERE property_id = :propertyId", nativeQuery = true)
   Double calculateAvgRatingForProperty(@Param("propertyId") int propertyId);

   @Modifying
   @Transactional
   @Query(value = "UPDATE properties p SET p.avgRating = :avgRating WHERE p.id = :propertyId")
   void updatePropertyAvgRating(@Param("propertyId") int propertyId, @Param("avgRating") Double avgRating);
}

