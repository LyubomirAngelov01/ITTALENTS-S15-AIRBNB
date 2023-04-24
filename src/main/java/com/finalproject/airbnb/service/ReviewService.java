package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.DeleteReviewDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReservationEntity;
import com.finalproject.airbnb.model.entities.ReviewEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ReviewService extends AbstractService {

    public ReviewInfoDTO createReview(int id, ReviewInfoDTO dto, int loggedId) {
        UserEntity u = getUserById(loggedId);
        PropertyEntity property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found!"));
        if (!reservationRepository.existsByUserAndProperty(u, property)) {
            throw new NotFoundException("No reservation under the property!");
        }
        ReservationEntity reservation = reservationRepository.findFirstByUserAndPropertyRAnd(u.getId(), property.getId());
        if (reservation.getCheckOutDate().isAfter(LocalDate.now())
                || LocalDate.now().isAfter(reservation.getCheckOutDate().plusMonths(1))) {
            throw new BadRequestException("You can only give a review no more than a month after checkout!");
        }
        if (reviewRepository.existsByOwnerAndProperty(u, property)) {
            throw new BadRequestException("Maximum of 1 review per visited property!");
        }
        ReviewEntity review = mapper.map(dto, ReviewEntity.class);
        review.setOwner(u);
        review.setProperty(property);
        if (review.getRating() > 5 || review.getRating() < 0) {
            throw new BadRequestException("Rating must be from 0 to 5!");
        }
        reviewRepository.save(review);
        if (property.getAvgRating() == 0) {
            property.setAvgRating(review.getRating());
        }
        double avgRating = reviewRepository.calculateAvgRatingForProperty(property.getId());
        reviewRepository.updatePropertyAvgRating(property.getId(), avgRating);
        propertyRepository.save(property);
        return mapper.map(review, ReviewInfoDTO.class);
    }

    public DeleteReviewDTO deleteReview(int reviewId, int loggedId) {
        UserEntity u = getUserById(loggedId);
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found!"));
        if (!reviewRepository.existsByOwner(u)) {
            throw new UnauthorizedException
                    ("Review is not owned by the user!");
        }
        if (review.getOwner().getId() != u.getId()) {
            throw new UnauthorizedException("Cannot delete another user's review!");
        }
        reviewRepository.delete(review);
        return new DeleteReviewDTO();
    }
}
