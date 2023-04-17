package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.DeleteReviewDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.Review;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends AbstractService {

    public ReviewInfoDTO createReview(int id, ReviewInfoDTO dto, int loggedId) {
        User u = getUserById(loggedId);
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found!"));
        Review review = mapper.map(dto, Review.class);
        review.setOwner(u);
        review.setProperty(property);
        reviewRepository.save(review);
        return mapper.map(review, ReviewInfoDTO.class);
    }

    public DeleteReviewDTO deleteReview(int id, int id2, int loggedId) {
        User u = getUserById(loggedId);
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found!"));
        Review review = reviewRepository.findById(id2)
                .orElseThrow(() -> new NotFoundException("Review not found!"));
        if(!reviewRepository.existsByPropertyIdAndUserId(property.getId(), u.getId())) {
            throw new UnauthorizedException
                    ("Review is not owned by the user or the property does not own the review!");
        }
        reviewRepository.delete(review);
        return new DeleteReviewDTO();
    }
}
