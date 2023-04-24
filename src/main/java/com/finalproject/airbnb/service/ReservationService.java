package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.ReservationDTO;
import com.finalproject.airbnb.model.DTOs.SuccessfulReservationDTO;
import com.finalproject.airbnb.model.DTOs.UpcomingReservationClientDTO;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReservationEntity;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService extends AbstractService {


    public SuccessfulReservationDTO makeReservation(int userId, int propertyId, ReservationDTO reservationDTO) {

        validDates(reservationDTO);
        if (reservationDTO.getCheckOutDate().equals(reservationDTO.getCheckInDate())){
            throw new BadRequestException("you have to select different dates for check in and check out");
        }

        checkAvailability(reservationDTO, propertyId);

        ReservationEntity reservation = new ReservationEntity();
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found"));

        if (property.getOwner().getId() == userId) {
            throw new BadRequestException("you can't make a reservation on your own property");
        }
        reservation.setUser(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found")));
        reservation.setProperty(property);
        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        if(reservationDTO.getGuests() > property.getMaxGuests()){
            throw new BadRequestException("Only " + property.getMaxGuests() + " available slots for guests!");
        }
        reservation.setGuests(reservationDTO.getGuests());
        reservationRepository.save(reservation);

        SuccessfulReservationDTO successfulReservation = mapper.map(reservation, SuccessfulReservationDTO.class);
        successfulReservation.setPropertyId(property.getId());


        return successfulReservation;

    }


    public Page<UpcomingReservationClientDTO> listUpcomingReservationsForAClient(int loggedId, Pageable pageable) {
        Page<ReservationEntity> reservations = reservationRepository.findAllByUserIdAndAndCheckInDateAfter(loggedId, LocalDate.now(),pageable);
        Page<UpcomingReservationClientDTO> upcomingReservations = reservations.map(reservationEntity -> new UpcomingReservationClientDTO(
                reservationEntity.getCheckInDate(),reservationEntity.getCheckOutDate(),reservationEntity.getProperty().getId(),reservationEntity.getProperty().getTitle()));

        return upcomingReservations;
    }


    public String removeReservation(int reservationId, int loggedId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("reservation not found"));
        if (reservation.getUser().getId() != loggedId) {
            throw new UnauthorizedException("you can remove your reservations only");
        }
        reservationRepository.deleteById(reservationId);
        return "successfully removed reservation";
    }


    private void checkAvailability(ReservationDTO dto, int propertyId) {
        List<ReservationEntity> reservationsForProperty = reservationRepository.findAllByPropertyId(propertyId);
        for (ReservationEntity reservation : reservationsForProperty) {
            if ((dto.getCheckInDate().isAfter(reservation.getCheckInDate()) && dto.getCheckInDate().isBefore(reservation.getCheckOutDate())) ||
                    (dto.getCheckOutDate().isAfter(reservation.getCheckInDate()) && dto.getCheckOutDate().isBefore(reservation.getCheckOutDate())) ||
                    ((dto.getCheckInDate().isBefore(reservation.getCheckInDate()) || dto.getCheckInDate().isEqual(reservation.getCheckInDate()))
                            && (dto.getCheckOutDate().isAfter(reservation.getCheckOutDate()) || dto.getCheckOutDate().isEqual(reservation.getCheckOutDate()))) ||
                    (dto.getCheckInDate().isEqual(reservation.getCheckInDate()) && dto.getCheckInDate().isEqual(reservation.getCheckInDate()))
            ) {
                throw new BadRequestException("These days are already reserved");
            }
        }
    }

    private void validDates(ReservationDTO reservationDTO) {
        if ((reservationDTO.getCheckInDate().isBefore(LocalDate.now())) ||
                (reservationDTO.getCheckOutDate().isBefore(reservationDTO.getCheckInDate()))) {
            throw new BadRequestException("select valid dates for reservation");
        }
    }
}
