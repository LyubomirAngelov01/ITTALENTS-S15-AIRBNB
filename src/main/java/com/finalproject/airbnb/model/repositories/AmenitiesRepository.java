package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Integer> {
}
