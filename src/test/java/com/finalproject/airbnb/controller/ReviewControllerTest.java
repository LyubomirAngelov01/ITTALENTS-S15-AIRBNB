package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.DeleteReviewDTO;
import com.finalproject.airbnb.model.DTOs.ReviewInfoDTO;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReservationEntity;
import com.finalproject.airbnb.model.entities.ReviewEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.ReservationRepository;
import com.finalproject.airbnb.model.repositories.ReviewRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {


    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void createReviewSuccess() {
        int propertyId = 1;
        int loggedId = 1;

        UserEntity user = new UserEntity();
        PropertyEntity property = new PropertyEntity();
        ReservationEntity reservation = new ReservationEntity();
        reservation.setCheckOutDate(LocalDate.now().minusDays(1));

        ReviewInfoDTO dto = new ReviewInfoDTO();
        dto.setComment("Great place!");
        dto.setRating(4.5);

        ReviewEntity review = new ReviewEntity();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setOwner(user);
        review.setProperty(property);

        when(userRepository.findById(loggedId)).thenReturn(Optional.of(user));
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(reservationRepository.existsByUserAndProperty(user, property)).thenReturn(true);
        when(reservationRepository.findByUserAndProperty(user, property)).thenReturn(reservation);
        when(reviewRepository.existsByOwnerAndProperty(user, property)).thenReturn(false);
        when(mapper.map(dto, ReviewEntity.class)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(mapper.map(review, ReviewInfoDTO.class)).thenReturn(dto);

        ReviewInfoDTO result = reviewService.createReview(propertyId, dto, loggedId);

        assertEquals(dto, result);
    }

    @Test
    void deleteReview() {
        int reviewId = 1;
        int loggedId = 1;

        UserEntity user = new UserEntity();
        ReviewEntity review = new ReviewEntity();

        when(userRepository.findById(loggedId)).thenReturn(Optional.of(user));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.existsByOwner(user)).thenReturn(true);

        DeleteReviewDTO result = reviewService.deleteReview(reviewId, loggedId);

        assertEquals("REVIEW HAS BEEN DELETED", result.getMsg());
        verify(reviewRepository, times(1)).delete(review);
    }
}