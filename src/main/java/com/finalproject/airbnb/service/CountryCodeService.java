package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.entities.CountryCode;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.CountryCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryCodeService extends AbstractService{
    private final CountryCodeRepository countryCodeRepository;

    public CountryCode findById(int id){
        return countryCodeRepository.findById(id).orElseThrow(()->new NotFoundException("country code not found"));
    }


}
