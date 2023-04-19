package com.finalproject.airbnb.service;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.*;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private static final Logger logger = LogManager.getLogger(UserService.class);


    public UserWithoutPasswordDTO register(RegisterDTO dto){
        validateRegisterDto(dto);

        User user = mapper.map(dto,User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCountryCode(countryCodeService.findById(dto.getCountryCode()));

        userRepository.save(user);

        emailService.sendEmail(generateMessageOnRegistration(dto));
        logger.info("New user registered with id " + user.getId());
        return  mapper.map(user, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO login (LoginDTO dto){
        User u = userRepository.getByEmail(dto.getEmail()).orElseThrow(()->new UnauthorizedException("Wrong credentials"));

        if(!encoder.matches(dto.getPassword(), u.getPassword())){
            logger.error("user " + u.getId() + " tried to log in with wrong credentials");
            throw new UnauthorizedException("Wrong credentials");
        }
        logger.info(u.getId() + " user logged in");
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
    public UserWithoutPasswordDTO checkProfile(int id){
        User u = getUserById(id);

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
        logger.info(id + " user have been deleted from the database");

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



    public UserWithoutPasswordDTO editLoginCredentials(int id, EditLoginCredentialsDTO dto) {
        User user = getUserById(id);
        if (!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new UnauthorizedException("wrong password");
        }
        if (dto.getPassword().equals(dto.getNewPassword())){
            throw new BadRequestException("your new password should be different from your previous");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())){
            throw new BadRequestException("Passwords mismatch");
        }

        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        logger.info(user.getId() + " user changed password");
        return mapper.map(user, UserWithoutPasswordDTO.class);

    }


    private void validateRegisterDto(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())){
            logger.error("a new user tried to register without passwords matching");
            throw new BadRequestException("Passwords mismatch");
        }
        if (userRepository.existsByEmail(dto.getEmail())){
            logger.error("a new user tried to register with already existing email");
            throw new BadRequestException("This email already exists!");
        }

        LocalDate validateYear = LocalDate.now().minusYears(Utility.VALID_AGE);
        if (dto.getBirthdate().isAfter(validateYear)){
            logger.error("a new user tried to register with invalid birthdate");
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

    public BecomeHostDTO setHostStatus(int id) {
        User user = getUserById(id);
        user.setHost(true);
        userRepository.save(user);
        logger.info(user.getId() + " became a host");

        return mapper.map(user, BecomeHostDTO.class);
    }
}
