package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface CountryCodeRepository extends JpaRepository<CountryCode,Integer> {

}
