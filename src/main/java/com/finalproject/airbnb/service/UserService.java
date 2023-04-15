package com.finalproject.airbnb.service;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.LoginDTO;
import com.finalproject.airbnb.model.DTOs.RegisterDTO;
import com.finalproject.airbnb.model.DTOs.UserWithoutPasswordDTO;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService extends AbstractService{

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final CountryCodeService countryCodeService;


    public UserWithoutPasswordDTO register(RegisterDTO dto){
        validateRegisterDto(dto);

        User user = mapper.map(dto,User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCountryCode(countryCodeService.findById(dto.getCountryCodeId()));

        userRepository.save(user);

        return  mapper.map(user, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO login (LoginDTO dto){
        Optional<User> u = userRepository.getByEmail(dto.getEmail());
        if(!u.isPresent()){
            throw new UnauthorizedException("Wrong credentials");
        }
        if(!encoder.matches(dto.getPassword(), u.get().getPassword())){
            throw new UnauthorizedException("Wrong credentials");
        }
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    private void validateRegisterDto(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.confirmPassword)){
            throw new BadRequestException("Passwords mismatch");
        }
        if (userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("This email already exists!");
        }

        LocalDate validateYear = LocalDate.now().minusYears(Utility.VALID_AGE);
        if (dto.getBirthdate().isAfter(validateYear)){
            throw new BadRequestException("not a valid birthdate");
        }
    }
}
