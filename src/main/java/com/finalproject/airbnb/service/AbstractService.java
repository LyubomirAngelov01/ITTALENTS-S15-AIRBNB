package com.finalproject.airbnb.service;


import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbstractService {

    @Autowired
    protected ModelMapper mapper;

    @Autowired
    protected UserRepository userRepository;// REMOVE LATER

    @Autowired
    protected PropertyRepository propertyRepository;// REMOVE LATER

    @Autowired
    protected CountryRepository countryRepository;// REMOVE LATER

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected PhotosRepository photosRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected AmenitiesRepository amenitiesRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
