package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReviewEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    @Query("SELECT COUNT(r) > 0 FROM reviews AS r WHERE r.property.id = :propertyId AND r.owner.id = :userId")
    boolean existsByPropertyIdAndUserId(@Param("propertyId") int propertyId, @Param("userId") int userId);

   List<ReviewEntity> findAllByProperty(PropertyEntity property);

   boolean existsByOwner(UserEntity owner);
}
