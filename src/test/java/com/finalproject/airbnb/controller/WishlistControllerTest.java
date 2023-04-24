package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.model.repositories.WishlistRepository;
import com.finalproject.airbnb.service.UserService;
import com.finalproject.airbnb.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistControllerTest {

    @InjectMocks
    private WishlistService wishlistService;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    private PropertyEntity property;
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        property = new PropertyEntity();
        property.setId(1);

        user = new UserEntity();
        user.setId(1);
    }

    @Test
    public void removeFromWishlist_success() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(wishlistRepository.existsByUserAndProperty(user, property)).thenReturn(true);

        String result = wishlistService.removeFromWishlist(1, 1);

        verify(wishlistRepository).deleteByUserAndProperty(user, property);
        assertEquals("property removed from the wishlist", result);
    }
}