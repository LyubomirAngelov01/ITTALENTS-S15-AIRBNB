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

    boolean existsByUserAndProperty(UserEntity user, PropertyEntity property);

    ReservationEntity findByUserAndProperty(UserEntity user, PropertyEntity property);

    void deleteByIdAndUserId(int id,int userId);
    List<ReservationEntity> findAllByPropertyId(int propertyId);
    List<ReservationEntity> findAllByProperty(PropertyEntity property);
    @Query("SELECT r FROM reservations AS r WHERE r.checkInDate > curdate()")
    List<ReservationEntity> getAllAfterCurrentDate();

    @Query(value = "SELECT COUNT(r.id) FROM reservations r " +
            "JOIN properties p ON r.property_id = p.id " +
            "WHERE r.user_id IN(:firstUserId,:secondUserId) AND p.owner_id IN(:firstUserId,:secondUserId);",nativeQuery = true)
    int reservationsBetweenUsers(int firstUserId,int secondUserId);
    void deleteById(int id);


}
