package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Photos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotosRepository extends JpaRepository<Photos, Integer> {

    List<Photos> findAllByPropertyId(int id);
    void deleteAllByPropertyId(int id);
}
