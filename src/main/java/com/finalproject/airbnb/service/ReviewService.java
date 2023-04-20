package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.DeleteReviewDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReviewEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends AbstractService {

    public ReviewInfoDTO createReview(int id, ReviewInfoDTO dto, int loggedId) {
        UserEntity u = getUserById(loggedId);
        PropertyEntity property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found!"));
        ReviewEntity review = mapper.map(dto, ReviewEntity.class);
        review.setOwner(u);
        review.setProperty(property);
        if(review.getRating() > 5 || review.getRating() < 0) {
            throw new BadRequestException("Rating must be from 0 to 5!");
        }
        if(property.getAvgRating() == 0){
            property.setAvgRating(review.getRating());
        }
            property.setAvgRating((property.getAvgRating() + review.getRating()) / 2);
        propertyRepository.save(property);
        reviewRepository.save(review);
        return mapper.map(review, ReviewInfoDTO.class);
    }

    public DeleteReviewDTO deleteReview(int reviewId, int loggedId) {
        UserEntity u = getUserById(loggedId);
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found!"));
        if(!reviewRepository.existsByOwner(u)) {
            throw new UnauthorizedException
                    ("Review is not owned by the user!");
        }
        reviewRepository.delete(review);
        return new DeleteReviewDTO();
    }
}
