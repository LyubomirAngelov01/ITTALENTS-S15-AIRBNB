package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.DeleteReviewDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "JWT token")
public class ReviewController extends AbstractController{

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/properties/{id}/reviews")
    public ReviewInfoDTO addReview(@PathVariable int id, @Valid @RequestBody ReviewInfoDTO dto, HttpSession s) {
        return reviewService.createReview(id, dto, getLoggedId(s));
    }

    @DeleteMapping("/reviews/{reviewId}")
    private DeleteReviewDTO deleteReview(@PathVariable("reviewId") int ReviewId, HttpSession s) {
        return reviewService.deleteReview(ReviewId, getLoggedId(s));
    }

}
