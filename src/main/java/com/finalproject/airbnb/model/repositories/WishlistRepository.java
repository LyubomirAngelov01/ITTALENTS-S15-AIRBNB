package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.compositeKeysClasses.WishlistKeys;
import com.finalproject.airbnb.model.entities.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistEntity, WishlistKeys> {

    List<WishlistEntity> findAllByUserId(int userId);

    void deleteByUserIdAndPropertyId(int userId,int propertyId);

}
