package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.ReservationDTO;
import com.finalproject.airbnb.model.DTOs.SuccessfulReservationDTO;
import com.finalproject.airbnb.model.DTOs.UpcomingReservationClientDTO;
import com.finalproject.airbnb.service.ReservationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "JWT token")
public class ReservationController extends AbstractController{
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/properties/{id}/reservations")
    public SuccessfulReservationDTO makeReservation(@PathVariable int id, @Valid @RequestBody ReservationDTO reservationDTO, HttpServletRequest request){
        int userId = extractUserIdFromToken(request);
        return reservationService.makeReservation(userId,id,reservationDTO);
    }

    @DeleteMapping("/users/reservations/{reservation_id}")
    public void removeReservation(@PathVariable("reservation_id") int reservationId, HttpServletRequest request){
        int userId = extractUserIdFromToken(request);
        reservationService.removeReservation(reservationId,userId);
    }
    @GetMapping("/users/reservations")
    public List<UpcomingReservationClientDTO> checkUpcomingReservations(HttpServletRequest request){
        int userId = extractUserIdFromToken(request);
        return reservationService.listUpcomingReservationsForAClient(userId);
    }
}
