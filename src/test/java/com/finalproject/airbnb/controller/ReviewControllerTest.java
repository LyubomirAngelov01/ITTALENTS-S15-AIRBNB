package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.Utility;
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
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ReviewService reviewService;
    @Mock
    private HttpSession session;

    @InjectMocks
    private ReviewService reviewService2;

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
    void createReview() {
        int userId = 1;
        int propertyId = 1;
        UserEntity user = new UserEntity();
        PropertyEntity property = new PropertyEntity();
        ReservationEntity reservation = new ReservationEntity();
        reservation.setCheckOutDate(LocalDate.now().minusDays(5));
        ReviewEntity review = new ReviewEntity();
        review.setRating(4);
        ReviewInfoDTO dto = new ReviewInfoDTO();
        dto.setRating(4.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(reservationRepository.existsByUserAndProperty(user, property)).thenReturn(true);
        when(reservationRepository.findByUserAndProperty(user, property)).thenReturn(reservation);
        when(reviewRepository.existsByOwnerAndProperty(user, property)).thenReturn(false);
        when(mapper.map(dto, ReviewEntity.class)).thenReturn(review);
        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(review);
        when(mapper.map(review, ReviewInfoDTO.class)).thenReturn(dto);
        when(propertyRepository.save(any(PropertyEntity.class))).thenReturn(property);
        when(reviewRepository.calculateAvgRatingForProperty(property.getId())).thenReturn(4.0);

        ReviewInfoDTO result = reviewService2.createReview(propertyId, dto, userId);

        assertNotNull(result);
        assertEquals(dto.getRating(), result.getRating());
    }
    @Test
    @SneakyThrows
    public void deleteReview() {
        int reviewId = 1;
        int loggedId = 2;
        DeleteReviewDTO deleteReviewDTO = new DeleteReviewDTO();
        when(reviewService.deleteReview(reviewId, loggedId)).thenReturn(deleteReviewDTO);

        MockHttpSession session1 = new MockHttpSession();

        when(session.getAttribute(Utility.LOGGED)).thenReturn(true);
        when(session.getAttribute(Utility.LOGGED_ID)).thenReturn(loggedId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reviews/{reviewId}", reviewId)
                        .session(session1)
                        .sessionAttr(Utility.LOGGED, true)
                        .sessionAttr(Utility.LOGGED_ID, loggedId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        verify(reviewService).deleteReview(reviewId, loggedId);

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, result.getResponse().getContentType());
        assertEquals("{\"msg\":\"REVIEW HAS BEEN DELETED\"}", result.getResponse().getContentAsString());
    }
}
