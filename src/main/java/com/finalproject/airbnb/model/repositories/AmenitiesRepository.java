package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Amenities;
import com.finalproject.airbnb.model.entities.Property;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Integer> {

    Amenities getByProperty(Property property);
}




