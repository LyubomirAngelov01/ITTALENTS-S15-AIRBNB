package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.WishlistPropertyDTO;
import com.finalproject.airbnb.model.entities.PhotosEntity;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.entities.WishlistEntity;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.WishlistRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<WishlistPropertyDTO> takeWishlistOfUser(int userId) {
        List<WishlistEntity> wishlists = wishlistRepository.findAllByUserId(userId);
        List<PropertyEntity> properties = wishlists.stream()
                .map(wishlist -> wishlist.getProperty())
                .collect(Collectors.toList());
        List<WishlistPropertyDTO> wishlistProperties = properties.stream()
                .map(property -> mapper.map(property, WishlistPropertyDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < properties.size(); i++) {
            List<PhotosEntity> photos = properties.get(i).getPhotos();
            List<String> urls = new ArrayList();
            for (PhotosEntity photo : photos) {
                urls.add(photo.getPhotoUrl());
            }
            wishlistProperties.get(i).setPhotosUrl(urls);
        }
        return wishlistProperties;
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

    public String removeFromWishlist(int propertyId, int loggedId) {
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found"));
        UserEntity user = userService.getUserById(loggedId);
        if (!wishlistRepository.existsByUserAndProperty(user,property)){
            throw new BadRequestException("you dont have that property in your wishlist");
        }
        wishlistRepository.deleteByUserAndProperty(user, property);
        return "property removed from the wishlist";
    }
}
