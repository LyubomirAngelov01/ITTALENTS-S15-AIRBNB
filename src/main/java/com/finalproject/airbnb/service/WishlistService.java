package com.finalproject.airbnb.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.finalproject.airbnb.model.DTOs.WishlistPropertyDTO;
import com.finalproject.airbnb.model.entities.Photos;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.entities.Wishlist;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
        List<Wishlist> wishlists = wishlistRepository.findAllByUserId(userId);
        List<Property> properties = wishlists.stream()
                .map(wishlist -> wishlist.getProperty())
                .collect(Collectors.toList());
        List<WishlistPropertyDTO> wishlistProperties = properties.stream()
                .map(property -> mapper.map(property, WishlistPropertyDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < properties.size(); i++) {
            List<Photos> photos = properties.get(i).getPhotos();
            List<String> urls = new ArrayList();
            for (Photos photo: photos) {
                urls.add(photo.getPhotoUrl());
            }
            wishlistProperties.get(i).setPhotosUrl(urls);
        }
        return wishlistProperties;
    }
    public void addToWishlist(int propertyId,int userId){
        Property property = propertyRepository.findById(propertyId).orElseThrow(()->new NotFoundException("Property not found"));
        User user = userService.getUserById(userId);
        wishlistRepository.save(new Wishlist(user,property));

    }

    @Transactional
    public void removeFromWishlist(int propertyId, int loggedId) {
         wishlistRepository.deleteByUserIdAndPropertyId(loggedId,propertyId);
    }
}
