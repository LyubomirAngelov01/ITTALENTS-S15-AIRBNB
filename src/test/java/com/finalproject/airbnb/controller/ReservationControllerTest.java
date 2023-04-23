package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.UpcomingReservationClientDTO;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReservationEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.repositories.ReservationRepository;
import com.finalproject.airbnb.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {


    @Mock
    private ReservationRepository reservationRepository;


    @InjectMocks
    private ReservationService reservationService;


    @Test
    void testListUpcomingReservationsForAClient() {
        int loggedId = 1;

        PropertyEntity property = new PropertyEntity();
        property.setId(1);
        property.setTitle("Test Property");

        ReservationEntity reservationEntity1 = new ReservationEntity();
        reservationEntity1.setCheckInDate(LocalDate.now().plusDays(5));
        reservationEntity1.setCheckOutDate(LocalDate.now().plusDays(10));
        reservationEntity1.setProperty(property);

        ReservationEntity reservationEntity2 = new ReservationEntity();
        reservationEntity2.setCheckInDate(LocalDate.now().plusDays(15));
        reservationEntity2.setCheckOutDate(LocalDate.now().plusDays(20));
        reservationEntity2.setProperty(property);

        List<ReservationEntity> reservations = new ArrayList<>();
        reservations.add(reservationEntity1);
        reservations.add(reservationEntity2);

        when(reservationRepository.findAllByUserIdAndAndCheckInDateAfter(loggedId, LocalDate.now())).thenReturn(reservations);

        List<UpcomingReservationClientDTO> upcomingReservations = reservationService.listUpcomingReservationsForAClient(loggedId);

        assertEquals(2, upcomingReservations.size());
        assertEquals(reservationEntity1.getCheckInDate(), upcomingReservations.get(0).getCheckInDate());
        assertEquals(reservationEntity1.getCheckOutDate(), upcomingReservations.get(0).getCheckOutDate());
        assertEquals(property.getId(), upcomingReservations.get(0).getPropertyId());
        assertEquals(property.getTitle(), upcomingReservations.get(0).getTitle());

        assertEquals(reservationEntity2.getCheckInDate(), upcomingReservations.get(1).getCheckInDate());
        assertEquals(reservationEntity2.getCheckOutDate(), upcomingReservations.get(1).getCheckOutDate());
        assertEquals(property.getId(), upcomingReservations.get(1).getPropertyId());
        assertEquals(property.getTitle(), upcomingReservations.get(1).getTitle());

        verify(reservationRepository).findAllByUserIdAndAndCheckInDateAfter(loggedId, LocalDate.now());
    }

    @Test
    void testRemoveReservation_success() {
        int reservationId = 1;
        int loggedId = 1;

        ReservationEntity reservation = new ReservationEntity();
        UserEntity user = new UserEntity();
        user.setId(loggedId);
        reservation.setUser(user);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        reservationService.removeReservation(reservationId, loggedId);

        verify(reservationRepository).findById(reservationId);
        verify(reservationRepository).deleteById(reservationId);
    }

}
