package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.composite_кeys.WishlistKeys;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.entities.WishlistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishlistEntity, WishlistKeys> {

    Page<WishlistEntity> findAllByUserId(int userId, Pageable pageable);

    void deleteByUserIdAndPropertyId(int userId, int propertyId);

    void deleteByUserAndProperty(UserEntity user,PropertyEntity property);

    boolean existsByUserAndProperty(UserEntity user, PropertyEntity property);
}
