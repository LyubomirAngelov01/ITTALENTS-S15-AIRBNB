package com.finalproject.airbnb.service;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.model.entities.CountryCode;
import com.finalproject.airbnb.model.entities.Reservation;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.CountryCodeRepository;
import com.finalproject.airbnb.model.repositories.ReservationRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService extends AbstractService{

    private final EmailService emailService;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final CountryCodeService   countryCodeService;
    private final ReservationRepository reservationRepository;
    private CountryCodeRepository countryCodeRepository;


    public UserWithoutPasswordDTO register(RegisterDTO dto){
        validateRegisterDto(dto);

        User user = mapper.map(dto,User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCountryCode(countryCodeService.findById(dto.getCountryCode()));

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

    @Transactional
    public UserWithoutPasswordDTO editProfileInfo(EditProfileInfoDTO dto, int id){
        User u = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));


        u.setEmail(dto.getEmail());
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setPhoneNumber(dto.getPhoneNumber());
        u.setCountryCode(countryCodeService.findById(dto.getCountryCode()));
        //mapper.map(dto,u);

        UserWithoutPasswordDTO user = mapper.map(u,UserWithoutPasswordDTO.class);
        return user;
    }



    public void deleteAccount(int id) {
        userRepository.deleteById(id);
    }



    public List<TripDTO> listAllTrips(int userId) {
        User u = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("user not found"));
        List <Reservation>  reservations = reservationRepository.findAllByUser(u);
        List<TripDTO> trips = reservations.stream()
                .map(reservation -> new TripDTO(reservation.getCheckInDate(),reservation.getCheckOutDate(),reservation.getProperty().getId(),reservation.getProperty().getTitle()))
                .collect(Collectors.toList());
        return trips;
    }



    public UserWithoutPasswordDTO editLoginCredetials(int id,EditLoginCredentialsDTO dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())){
            throw new BadRequestException("Passwords mismatch");
        }

        User user = userRepository.findById(id).orElseThrow(()-> new UnauthorizedException("not a valid user"));

        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        return mapper.map(user, UserWithoutPasswordDTO.class);

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

    private SimpleMessageDTO generateMessageOnRegistration(RegisterDTO registerDTO){
        SimpleMessageDTO dto = new SimpleMessageDTO();
        dto.setRecipient(registerDTO.getEmail());
        dto.setContent("Welcome to Airbnb " + registerDTO.getFirstName() + " " + registerDTO.getLastName() + "!");
        dto.setSubject("Registration");
        return dto;
    }
}
