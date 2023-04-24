package com.finalproject.airbnb.service;


import com.finalproject.airbnb.model.entities.UserEntity;
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
    protected UserRepository userRepository;

    @Autowired
    protected PropertyRepository propertyRepository;

    @Autowired
    protected CountryRepository countryRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected PhotosRepository photosRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected AmenitiesRepository amenitiesRepository;

    @Autowired
    protected ReservationRepository reservationRepository;

    @Autowired
    protected WishlistRepository wishlistRepository;

    @Autowired
    protected MessageRepository messageRepository;

    protected UserEntity getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
