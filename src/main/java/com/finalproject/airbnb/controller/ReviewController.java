package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.DeleteReviewDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController extends AbstractController{

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/properties/{id}/reviews")
    public ReviewInfoDTO addReview(@PathVariable int id, @RequestBody ReviewInfoDTO dto, HttpSession s) {
        return reviewService.createReview(id, dto, getLoggedId(s));
    }

    @DeleteMapping("/properties/{id}/reviews/{id2}")
    private DeleteReviewDTO deleteReview(@PathVariable("id") int id, @PathVariable("id2") int id2, HttpSession s) {
        return reviewService.deleteReview(id, id2, getLoggedId(s));
    }
}
