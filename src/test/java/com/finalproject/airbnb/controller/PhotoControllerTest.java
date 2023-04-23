package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.DeleteAllPhotosDTO;
import com.finalproject.airbnb.model.entities.PhotosEntity;
import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.repositories.PhotosRepository;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.service.PhotoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoControllerTest {

    @Mock
    private PhotosRepository photosRepository;

    @Mock
    private PropertyRepository propertyRepository;


    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private PhotoService photoService;


    @Test
    public void deleteAllPhotos() {
        int propertyId = 1;
        int loggedUserId = 2;

        UserEntity user = new UserEntity();
        user.setId(loggedUserId);
        PropertyEntity property = new PropertyEntity();
        property.setId(propertyId);
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(userRepository.findById(loggedUserId)).thenReturn(Optional.of(user));
        when(propertyRepository.userOwnsProperty(loggedUserId, propertyId)).thenReturn(true);

        List<PhotosEntity> photos = new ArrayList<>();
        photos.add(new PhotosEntity());
        when(photosRepository.findAllByPropertyId(propertyId)).thenReturn(photos);

        DeleteAllPhotosDTO result = photoService.deleteAllPhotos(propertyId, loggedUserId);

        Mockito.verify(photosRepository).deleteAllByPropertyId(propertyId);
        Assertions.assertEquals("ALL PHOTOS HAVE BEEN DELETED", result.getMsg());
    }
}