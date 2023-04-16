package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.compositeKeysClasses.WishlistKeys;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, WishlistKeys> {
    List<Property> findAllByUser(User user);
}
