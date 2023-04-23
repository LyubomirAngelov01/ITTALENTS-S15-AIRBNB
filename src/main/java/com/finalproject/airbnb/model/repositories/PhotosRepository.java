package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.PhotosEntity;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotosRepository extends JpaRepository<PhotosEntity, Integer> {

    void deleteAllByPropertyId(int id);

    List<PhotosEntity> findAllByPropertyId(int id);

    List<PhotosEntity> findByProperty(PropertyEntity property);

}
