package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.entities.Address;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.CountryRepository;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbstractService {

    protected final ModelMapper mapper;

    protected final UserRepository userRepository;

    protected final PropertyRepository propertyRepository;
    protected final CountryRepository countryRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected Address fillAddress(Property property, int id){
        int countryId = property.getAddress().getCountry().getId();
        if(countryRepository.findCountriesById(countryId) == true)
    }
}
