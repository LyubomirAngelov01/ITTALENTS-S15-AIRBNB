package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.ReservationDTO;
import com.finalproject.airbnb.model.DTOs.SuccessfulReservationDTO;
import com.finalproject.airbnb.model.DTOs.UpcomingReservationClientDTO;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.Reservation;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.ReservationRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

        Property property = propertyRepository.findById(propertyId).orElseThrow(()->new NotFoundException("Property not found"));
        reservation.setUser(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("user not found")));
        reservation.setProperty(property);
        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        reservation.setGuests(reservationDTO.getGuests());
        reservationRepository.save(reservation);

        SuccessfulReservationDTO successfulReservation = mapper.map(reservation,SuccessfulReservationDTO.class);
        successfulReservation.setPropertyId(property.getId());


        return successfulReservation;

    }


    public List<UpcomingReservationClientDTO> listUpcomingReservationsForAClient(int loggedId) {
        List<Reservation> reservations = reservationRepository.findAllByUserIdAndAndCheckInDateAfter(loggedId, LocalDate.now());
        List<UpcomingReservationClientDTO> upcomingReservations = reservations.stream()
                .map(reservation -> new UpcomingReservationClientDTO(
                        reservation.getCheckInDate(),reservation.getCheckOutDate(),reservation.getProperty().getId(),reservation.getProperty().getTitle()))
                .collect(Collectors.toList());

        return upcomingReservations;

    }


    @Transactional
    public void removeReservation(int reservationId, int loggedId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new NotFoundException("reservation not found"));
        if (reservation.getUser().getId() != loggedId){
            throw new UnauthorizedException("you can remove your reservations only");
        }
        reservationRepository.deleteById(reservationId);
    }





    private boolean validDates(ReservationDTO reservationDTO,int propertyId) {
        if ((reservationDTO.getCheckInDate().isBefore(LocalDate.now())) ||
                (reservationDTO.getCheckOutDate().isBefore(reservationDTO.getCheckInDate().plusDays(1)))){
            return false;
        }
//        Property property = propertyRepository.findById(propertyId).orElseThrow(()-> new NotFoundException("property not found"));
//        List<Reservation> reservations = reservationRepository.findAllByProperty(property);
//        for (Reservation reservation: reservations) {
////            if((reservationDTO.getCheckInDate().isAfter(reservation.getCheckInDate().minusDays(1))) ||
////                    (reservationDTO.getCheckOutDate().isBefore(reservation.getCheckOutDate().plusDays(1))) ||
////                    (reservationDTO.getCheckInDate().isBefore(reservation.getCheckOutDate().plusDays(1))) ||
////                    (reservationDTO.getCheckOutDate().isAfter(reservation.getCheckInDate().minusDays(1)))){
//            if (reservationDTO.getCheckInDate().isAfter(reservation.getCheckInDate()) ||
//                reservationDTO.getCheckOutDate().isBefore(reservation.getCheckOutDate())){
//                return false;
//            }
//        }
        return true;
    }

}
