package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryCodeRepository extends JpaRepository<CountryCode,Integer> {

}
