package com.finalproject.airbnb.service;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class UserService extends AbstractService{

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryCodeService countryCodeService;


    public UserWithoutPasswordDTO register(RegisterDTO dto){
        validateRegisterDto(dto);

        User user = mapper.map(dto,User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCountryCode(countryCodeService.findById(dto.getCountryCodeId()));

        userRepository.save(user);

        emailService.sendEmail(generateMessageOnRegistration(dto));
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
    public UserWithoutPasswordDTO checkProfile(int id){
        Optional <User> opt = userRepository.findById(id);
        if (!opt.isPresent()){
            throw new NotFoundException("user not found");
        }
        User u = opt.get();
        return mapper.map(u,UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO editProfileInfo(EditProfileInfoDTO dto, int id){
        User u = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));

        mapper.map(dto,u);
        u.setCountryCode(countryCodeService.findById(dto.getCountryCodeId()));
        userRepository.save(u);

        UserWithoutPasswordDTO user = mapper.map(u,UserWithoutPasswordDTO.class);
        return user;
    }

    private void validateRegisterDto(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())){
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

    public DeletedAccountDTO deleteAccount(int id) {
        User u = userRepository.findById(id).orElseThrow(()-> new NotFoundException("user not found"));
        userRepository.delete(u);
        return new DeletedAccountDTO();
    }

    private SimpleMessageDTO generateMessageOnRegistration(RegisterDTO registerDTO){
        SimpleMessageDTO dto = new SimpleMessageDTO();
        dto.setRecipient(registerDTO.getEmail());
        dto.setContent("Welcome to Airbnb " + registerDTO.getFirstName() + " " + registerDTO.getLastName() + "!");
        dto.setSubject("Registration");
        return dto;
    }
}
