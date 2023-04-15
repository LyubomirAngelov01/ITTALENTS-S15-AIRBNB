package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotosRepository extends JpaRepository<Photos, Integer> {
}
