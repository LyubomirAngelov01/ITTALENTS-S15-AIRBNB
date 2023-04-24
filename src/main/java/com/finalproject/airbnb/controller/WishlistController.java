package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.WishlistPropertyDTO;
import com.finalproject.airbnb.service.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "JWT token")
public class WishlistController extends AbstractController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/users/wishlist")
    public List<WishlistPropertyDTO> wishlist(HttpServletRequest request) {
        int id = extractUserIdFromToken(request);
        return wishlistService.takeWishlistOfUser(id);
    }

    @PostMapping("/properties/{propertyId}/wishlist")
    public String addToWishlist(@PathVariable int propertyId, HttpServletRequest request) {
        int id = extractUserIdFromToken(request);
        return wishlistService.addToWishlist(propertyId, id);
    }

    @DeleteMapping("/users/{propertyId}/wishlist")
    public String removeFromWishlist(@PathVariable int propertyId, HttpServletRequest request) {
        int id = extractUserIdFromToken(request);
        return wishlistService.removeFromWishlist(propertyId, id);
    }
}
