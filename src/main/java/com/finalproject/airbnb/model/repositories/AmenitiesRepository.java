package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.AmenitiesEntity;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<AmenitiesEntity, Integer> {

    AmenitiesEntity getByProperty(PropertyEntity property);


}




