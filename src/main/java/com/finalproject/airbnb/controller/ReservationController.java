package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.ReservationDTO;
import com.finalproject.airbnb.model.DTOs.SuccessfulReservationDTO;
import com.finalproject.airbnb.model.DTOs.UpcomingReservationClientDTO;
import com.finalproject.airbnb.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController extends AbstractController{
    private final ReservationService reservationService;

    @PostMapping("/properties/{id}/reservations")
    public SuccessfulReservationDTO makeReservation(@PathVariable int id, @Valid @RequestBody ReservationDTO reservationDTO, HttpSession session){
        int userId = getLoggedId(session);
        return reservationService.makeReservation(userId,id,reservationDTO);
    }

    @DeleteMapping("/users/reservations/{reservation_id}")
    public void removeReservation(@PathVariable("reservation_id") int reservationId, HttpSession session){
        reservationService.removeReservation(reservationId,getLoggedId(session));
    }
    @GetMapping("/users/reservations")
    public List<UpcomingReservationClientDTO> checkUpcomingReservations(HttpSession session){
        return reservationService.listUpcomingReservationsForAClient(getLoggedId(session));
    }
}
