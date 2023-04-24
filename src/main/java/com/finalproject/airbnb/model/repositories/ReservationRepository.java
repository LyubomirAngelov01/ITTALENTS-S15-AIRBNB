package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.ReservationEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Integer> {

    List<ReservationEntity> findAllByUser(UserEntity user);

    List<ReservationEntity> findAllByUserIdAndAndCheckInDateAfter(int userId, LocalDate checkInDate);

    boolean existsByUserAndProperty(UserEntity user, PropertyEntity property);

    ReservationEntity findByUserAndProperty(UserEntity user, PropertyEntity property);

    @Query(value = "SELECT * FROM reservations r WHERE r.user_id = :user_id AND r.property_id = :property_id ORDER BY r.check_out_date LIMIT 1", nativeQuery = true)
    ReservationEntity findFirstByUserAndPropertyRAnd(@Param("user_id") int userId, @Param("property_id") int propertyId);

    List<ReservationEntity> findAllByPropertyId(int propertyId);

    @Query("SELECT r FROM reservations AS r WHERE r.checkInDate > curdate()")
    List<ReservationEntity> getAllAfterCurrentDate();

    @Query(value = "SELECT COUNT(r.id) FROM reservations r " +
            "JOIN properties p ON r.property_id = p.id " +
            "WHERE r.user_id IN(:firstUserId,:secondUserId) AND p.owner_id IN(:firstUserId,:secondUserId);",nativeQuery = true)
    int reservationsBetweenUsers(int firstUserId,int secondUserId);
    void deleteById(int id);


}
