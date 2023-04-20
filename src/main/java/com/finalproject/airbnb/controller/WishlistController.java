package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.WishlistPropertyDTO;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class WishlistController extends AbstractController{

    private final WishlistService wishlistService;
    private final UserRepository userRepository;
    @GetMapping("/users/wishlist")
    public List<WishlistPropertyDTO> wishlist(HttpSession session){
        return wishlistService.takeWishlistOfUser(getLoggedId(session));
    }
    @PostMapping("/properties/{propertyId}/wishlist")
    public void addToWishlist(@PathVariable int propertyId, HttpSession session){
        wishlistService.addToWishlist(propertyId,getLoggedId(session));
    }

    @DeleteMapping("/users/{propertyId}/wishlist")
    public void removeFromWishlist(@PathVariable int propertyId,HttpSession session){
        wishlistService.removeFromWishlist(propertyId,getLoggedId(session));
    }

}
