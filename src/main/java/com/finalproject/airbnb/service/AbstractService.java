package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.PropertyInfoDTO;
import com.finalproject.airbnb.model.entities.Photos;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.CountryRepository;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.ReviewRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

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

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
