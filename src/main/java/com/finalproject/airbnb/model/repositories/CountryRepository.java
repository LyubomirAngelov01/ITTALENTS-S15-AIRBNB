package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean findCountriesById(int id);
}
