package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.DeleteReviewDTO;
import com.finalproject.airbnb.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ReviewService reviewService;
    @Mock
    private HttpSession session;


    @Test
    public void deleteReview_ReturnsDeleteReviewDTO() throws Exception {
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
