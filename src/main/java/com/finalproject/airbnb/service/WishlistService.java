package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.WishlistPropertyDTO;
import com.finalproject.airbnb.model.entities.PhotosEntity;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.entities.WishlistEntity;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.WishlistRepository;
import jakarta.transaction.Transactional;
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

public class WishlistService extends AbstractService{
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    public List<WishlistPropertyDTO> takeWishlistOfUser(int userId){
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
            for (PhotosEntity photo: photos) {
                urls.add(photo.getPhotoUrl());
            }
            wishlistProperties.get(i).setPhotosUrl(urls);
        }
        return wishlistProperties;
    }
    public void addToWishlist(int propertyId,int userId){
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(()->new NotFoundException("Property not found"));
        UserEntity user = userService.getUserById(userId);
        wishlistRepository.save(new WishlistEntity(user,property));

    }

    @Transactional
    public void removeFromWishlist(int propertyId, int loggedId) {
         wishlistRepository.deleteByUserIdAndPropertyId(loggedId,propertyId);
    }
}
