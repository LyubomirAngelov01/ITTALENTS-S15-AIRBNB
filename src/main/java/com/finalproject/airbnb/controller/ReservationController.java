package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.ReservationDTO;
import com.finalproject.airbnb.model.DTOs.SuccessfulReservationDTO;
import com.finalproject.airbnb.model.entities.Reservation;
import com.finalproject.airbnb.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController extends AbstractController{
    private final ReservationService reservationService;

    @PostMapping("/properties/{id}/reservations")
    public SuccessfulReservationDTO makeReservation(@PathVariable int id, @RequestBody ReservationDTO reservationDTO, HttpSession session){
        int userId = getLoggedId(session);
        return reservationService.makeReservation(userId,id,reservationDTO);
    }

    @DeleteMapping("/users/reservations/{id}")
    public void removeReservation(@PathVariable int id, HttpSession session){
        reservationService.removeReservation(id,getLoggedId(session));
    }
    @GetMapping("/users/reservations")
    public List<Reservation> checkUpcomingReservations(HttpSession session){
        return reservationService.listUpcomingReservationsForAClient(getLoggedId(session));
    }
}
