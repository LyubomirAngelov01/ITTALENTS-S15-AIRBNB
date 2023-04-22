package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.ReservationDTO;
import com.finalproject.airbnb.model.DTOs.SuccessfulReservationDTO;
import com.finalproject.airbnb.model.DTOs.UpcomingReservationClientDTO;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReservationEntity;
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
public class ReservationService extends AbstractService{



    public SuccessfulReservationDTO makeReservation(int userId, int propertyId, ReservationDTO reservationDTO){
        validDates(reservationDTO,propertyId);

        checkAvailability(reservationDTO,propertyId);

        ReservationEntity reservation = new ReservationEntity();
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(()->new NotFoundException("Property not found"));

        if (property.getOwner().getId() == userId){
            throw new BadRequestException("you can't make a reservation on your own property");
        }
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
        List<ReservationEntity> reservations = reservationRepository.findAllByUserIdAndAndCheckInDateAfter(loggedId, LocalDate.now());
        List<UpcomingReservationClientDTO> upcomingReservations = reservations.stream()
                .map(reservation -> new UpcomingReservationClientDTO(
                        reservation.getCheckInDate(),reservation.getCheckOutDate(),reservation.getProperty().getId(),reservation.getProperty().getTitle()))
                .collect(Collectors.toList());

        return upcomingReservations;

    }


    @Transactional
    public void removeReservation(int reservationId, int loggedId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId).orElseThrow(()->new NotFoundException("reservation not found"));
        if (reservation.getUser().getId() != loggedId){
            throw new UnauthorizedException("you can remove your reservations only");
        }
        reservationRepository.deleteById(reservationId);
    }




    private void checkAvailability(ReservationDTO dto,int propertyId){
        List<ReservationEntity> reservationsForProperty = reservationRepository.findAllByPropertyId(propertyId);
        for(ReservationEntity reservation:reservationsForProperty){
            if((dto.getCheckInDate().isAfter(reservation.getCheckInDate()) && dto.getCheckInDate().isBefore(reservation.getCheckOutDate()))||
                    (dto.getCheckOutDate().isAfter(reservation.getCheckInDate()) && dto.getCheckOutDate().isBefore(reservation.getCheckOutDate()))||
                    ((dto.getCheckInDate().isBefore(reservation.getCheckInDate())||dto.getCheckInDate().isEqual(reservation.getCheckInDate()))
                            &&( dto.getCheckOutDate().isAfter(reservation.getCheckOutDate())||dto.getCheckOutDate().isEqual(reservation.getCheckOutDate())))||
                    (dto.getCheckInDate().isEqual(reservation.getCheckInDate())&&dto.getCheckInDate().isEqual(reservation.getCheckInDate()))
            ){
                throw new BadRequestException("These days are already reserved");
            }
        }
    }
    private void validDates(ReservationDTO reservationDTO,int propertyId) {
        if ((reservationDTO.getCheckInDate().isBefore(LocalDate.now())) ||
                (reservationDTO.getCheckOutDate().isBefore(reservationDTO.getCheckInDate()))){
            throw new BadRequestException("select valid dates for reservation");
        }

    }

}
