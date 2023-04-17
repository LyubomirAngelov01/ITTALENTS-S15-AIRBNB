package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.Reservation;
import com.finalproject.airbnb.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    LocalDate findByCheckInDate(LocalDate date);
    LocalDate findByCheckOutDate(LocalDate date);
    List<Reservation> findAllByUser(User user);
    List<Reservation> findAllByUserIdAndAndCheckInDateAfter(int userId,LocalDate checkInDate);
    void deleteByIdAndUserId(int id,int userId);
    List<Reservation> findAllByProperty(Property property);

}
