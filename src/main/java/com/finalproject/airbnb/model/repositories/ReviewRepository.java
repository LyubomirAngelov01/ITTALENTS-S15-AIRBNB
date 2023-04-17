package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT COUNT(r) > 0 FROM reviews AS r WHERE r.property.id = :propertyId AND r.owner.id = :userId")
    boolean existsByPropertyIdAndUserId(@Param("propertyId") int propertyId, @Param("userId") int userId);
}
