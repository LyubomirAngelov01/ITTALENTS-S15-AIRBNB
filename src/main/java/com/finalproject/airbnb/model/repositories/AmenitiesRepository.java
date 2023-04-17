package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Amenities;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Integer> {

//    @Modifying
//    @Transactional
//    @Query("INSERT INTO amenities (wifi_connection, pool, tv, parking, air_conditioning, barbecue, smoke_alarm, first_aid, fire_extinguisher, gym, washer, kitchen, property_id)" +
//            " VALUES (:wifiConnection, :pool, :tv, :parking, :airConditioning, :barbecue, :smokeAlarm, :firstAid, :fireExtinguisher, :gym, :washer, :kitchen, :propertyId)")
//    void saveAmenity(@Param("wifiConnection") boolean wifiConnection, @Param("pool") boolean pool, @Param("tv") boolean tv, @Param("parking") boolean parking, @Param("airConditioning") boolean airConditioning
//            , @Param("barbecue") boolean barbecue, @Param("smokeAlarm") boolean smokeAlarm, @Param("firstAid") boolean firstAid, @Param("fireExtinguisher") boolean fireExtinguisher,
//                     @Param("gym") boolean gym, @Param("washer") boolean washer, @Param("kitchen") boolean kitchen, @Param("propertyId") int propertyId);
}


