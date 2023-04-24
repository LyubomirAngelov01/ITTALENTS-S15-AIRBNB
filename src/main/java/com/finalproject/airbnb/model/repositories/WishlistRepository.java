package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.compositeKeysClasses.WishlistKeys;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.entities.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistEntity, WishlistKeys> {

    List<WishlistEntity> findAllByUserId(int userId);

    void deleteByUserAndProperty(UserEntity user,PropertyEntity property);

    boolean existsByUserAndProperty(UserEntity user, PropertyEntity property);
}
