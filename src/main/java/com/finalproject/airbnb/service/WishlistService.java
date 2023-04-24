package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.WishlistPropertyDTO;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.entities.WishlistEntity;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class WishlistService extends AbstractService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    public Page<WishlistPropertyDTO> takeWishlistOfUser(int userId, Pageable pageable) {
        Page<WishlistEntity> wishlists = wishlistRepository.findAllByUserId(userId,pageable);
        Page<WishlistPropertyDTO> wishlist = wishlists.map(wishlistEntity -> new WishlistPropertyDTO(wishlistEntity.getProperty().getId(),wishlistEntity.getProperty().getTitle(),
                wishlistEntity.getProperty().getAvgRating(),wishlistEntity.getProperty().getPrice()));
        return wishlist;
    }

    public String addToWishlist(int propertyId, int userId) {
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found"));
        UserEntity user = userService.getUserById(userId);
        if (wishlistRepository.existsByUserAndProperty(user,property)){
            throw new BadRequestException("you already have that property in your wishlist");
        }
        wishlistRepository.save(new WishlistEntity(user, property));
        return "added to wishlist";
    }

    @Transactional
    public String removeFromWishlist(int propertyId, int loggedId) {
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found"));
        UserEntity user = userRepository.findById(loggedId).orElseThrow(() -> new NotFoundException("User not found!"));
        if (!wishlistRepository.existsByUserAndProperty(user,property)){
            throw new BadRequestException("you dont have that property in your wishlist");
        }
        wishlistRepository.deleteByUserAndProperty(user, property);
        return "property removed from the wishlist";
    }
}
