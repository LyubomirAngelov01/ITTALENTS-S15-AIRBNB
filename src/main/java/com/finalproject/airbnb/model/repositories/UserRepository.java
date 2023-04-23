package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    boolean existsByEmail(String email);

    Optional<UserEntity> getByEmail(String email);

    void deleteById(int id);

}
