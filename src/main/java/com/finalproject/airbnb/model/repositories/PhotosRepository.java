package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Photos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotosRepository extends JpaRepository<Photos, Integer> {
}
