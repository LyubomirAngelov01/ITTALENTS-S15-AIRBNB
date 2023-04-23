package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.repositories.WishlistRepository;
import com.finalproject.airbnb.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistControllerTest {

    @InjectMocks
    private WishlistService wishlistService;

    @Mock
    private WishlistRepository wishlistRepository;


    @BeforeEach
    public void setUp() {
        wishlistRepository = mock(WishlistRepository.class);
        wishlistService = new WishlistService();
        wishlistService.setWishlistRepository(wishlistRepository);
    }

    @Test
    public void removeFromWishlist() {
        int propertyId = 1;
        int loggedId = 2;

        wishlistService.removeFromWishlist(propertyId, loggedId);

        verify(wishlistRepository, times(1)).deleteByUserIdAndPropertyId(loggedId, propertyId);
    }
}