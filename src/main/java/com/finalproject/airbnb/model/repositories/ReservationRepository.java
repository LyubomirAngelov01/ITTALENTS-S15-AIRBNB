package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReservationEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Integer> {

    LocalDate findByCheckInDate(LocalDate date);
    LocalDate findByCheckOutDate(LocalDate date);
    List<ReservationEntity> findAllByUser(UserEntity user);
    List<ReservationEntity> findAllByUserIdAndAndCheckInDateAfter(int userId, LocalDate checkInDate);
    void deleteByIdAndUserId(int id,int userId);
    List<ReservationEntity> findAllByPropertyId(int propertyId);
    List<ReservationEntity> findAllByProperty(PropertyEntity property);
    @Query("SELECT r FROM reservations AS r WHERE r.checkInDate > curdate()")
    List<ReservationEntity> getAllAfterCurrentDate();

    public void deleteById(int id);


}
