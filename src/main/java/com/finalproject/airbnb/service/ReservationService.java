package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.ReservationDTO;
import com.finalproject.airbnb.model.DTOs.SuccessfulReservationDTO;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.Reservation;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.ReservationRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService extends AbstractService{

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public SuccessfulReservationDTO makeReservation(int userId, int propertyId, ReservationDTO reservationDTO){
        if (!validDates(reservationDTO,propertyId)){
            throw new BadRequestException("select valid dates for reservation");
        }
        Reservation reservation = new Reservation();

        reservation.setUser(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("user not found")));
        reservation.setProperty(propertyRepository.findById(propertyId).orElseThrow(()->new NotFoundException("Property not found")));
        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        reservation.setGuests(reservationDTO.getGuests());

        SuccessfulReservationDTO successfulReservation = mapper.map(reservation,SuccessfulReservationDTO.class);
        reservationRepository.save(reservation);

        successfulReservation.setMsg("Reservation successful for " + reservation.getProperty().getTitle() + " from "
                + reservation.getCheckInDate() + " to " + reservation.getCheckOutDate() + "!");

        return successfulReservation;

    }


    public List<Reservation> listUpcomingReservationsForAClient(int loggedId) {
        return reservationRepository.findAllByUserIdAndAndCheckInDateAfter(loggedId, LocalDate.now());
    }


    public void removeReservation(int reservationId, int loggedId) {
        reservationRepository.deleteByIdAndUserId(reservationId,loggedId);
    }





    private boolean validDates(ReservationDTO reservationDTO,int propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(()-> new NotFoundException("property not found"));
        List<Reservation> reservations = reservationRepository.findAllByProperty(property);
        for (Reservation reservation: reservations) {
            if((reservationDTO.getCheckInDate().isAfter(reservation.getCheckInDate().minusDays(1))) ||
                    (reservationDTO.getCheckOutDate().isBefore(reservation.getCheckOutDate().plusDays(1))) ||
                    (reservationDTO.getCheckInDate().isBefore(reservation.getCheckOutDate().plusDays(1))) ||
                    (reservationDTO.getCheckOutDate().isAfter(reservation.getCheckInDate().minusDays(1)))){
                return false;
            }
        }
        return true;
    }

}
