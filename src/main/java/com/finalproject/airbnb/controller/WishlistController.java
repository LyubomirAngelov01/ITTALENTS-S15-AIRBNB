package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.WishlistPropertyDTO;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
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
    public List<WishlistPropertyDTO> wishlist(HttpServletRequest request){
        int id = extractUserIdFromToken(request);
        return wishlistService.takeWishlistOfUser(id);
    }
    @PostMapping("/properties/{propertyId}/wishlist")
    public void addToWishlist(@PathVariable int propertyId, HttpServletRequest request){
        int id = extractUserIdFromToken(request);
        wishlistService.addToWishlist(propertyId,id);
    }

    @DeleteMapping("/users/{propertyId}/wishlist")
    public void removeFromWishlist(@PathVariable int propertyId,HttpServletRequest request){
        int id = extractUserIdFromToken(request);
        wishlistService.removeFromWishlist(propertyId,id);
    }

}
