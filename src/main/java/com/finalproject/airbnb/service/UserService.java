package com.finalproject.airbnb.service;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.model.entities.ReservationEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.CountryCodeRepository;
import com.finalproject.airbnb.model.repositories.ReservationRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class UserService extends AbstractService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountryCodeRepository countryCodeRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private static Logger logger = LogManager.getLogger(UserService.class);
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtService jwtService;


    public UserWithoutPasswordDTO register(RegisterDTO dto) {
        validateRegisterDto(dto);

        UserEntity user = mapper.map(dto, UserEntity.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCountryCode(countryCodeRepository.findById(dto.getCountryCode()).
                orElseThrow(() -> new NotFoundException("Select a valid country code!")));

        userRepository.save(user);

        emailService.sendEmail(generateMessageOnRegistration(dto));
        logger.info("New user registered with id " + user.getId());

        return mapper.map(user, UserWithoutPasswordDTO.class);
    }

    public TokenDTO login(LoginDTO dto) {
        UserEntity user = userRepository.getByEmail(dto.getEmail()).orElseThrow(() -> new UnauthorizedException("Wrong credentials"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            logger.error("user " + user.getId() + " tried to log in with wrong credentials");
            throw new UnauthorizedException("Wrong credentials");
        }
        String token = jwtService.generateToken(user);

        logger.info(user.getId() + " user logged in");
        return new TokenDTO(token);
    }

    public UserWithoutPasswordDTO checkProfile(int id) {
        UserEntity u = getUserById(id);

        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO editProfileInfo(EditProfileInfoDTO dto, int id) {
        UserEntity u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));


        u.setEmail(dto.getEmail());
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setPhoneNumber(dto.getPhoneNumber());
        u.setCountryCode(countryCodeRepository.findById(dto.getCountryCode()).
                orElseThrow(() -> new NotFoundException("Select a valid country code!")));
        userRepository.save(u);

        UserWithoutPasswordDTO user = mapper.map(u, UserWithoutPasswordDTO.class);
        return user;
    }


    public void deleteAccount(int id) {
        logger.info(id + " user have been deleted from the database");

        userRepository.deleteById(id);
    }


    public List<TripDTO> listAllTrips(int userId) {
        UserEntity u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
        List<ReservationEntity> reservations = reservationRepository.findAllByUser(u);
        List<TripDTO> trips = reservations.stream()
                .map(reservation -> new TripDTO(reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation.getProperty().getId(), reservation.getProperty().getTitle()))
                .collect(Collectors.toList());
        return trips;
    }


    public UserWithoutPasswordDTO editLoginCredentials(int id, EditLoginCredentialsDTO dto) {
        UserEntity user = getUserById(id);
        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("wrong password");
        }
        if (dto.getPassword().equals(dto.getNewPassword())) {
            throw new BadRequestException("your new password should be different from your previous");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("Passwords mismatch");
        }

        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        logger.info(user.getId() + " user changed password");
        return mapper.map(user, UserWithoutPasswordDTO.class);

    }


    public BecomeHostDTO setHostStatus(int id) {
        UserEntity user = getUserById(id);
        if (user.isHost() == true) {
            throw new BadRequestException("already a host");
        }
        user.setHost(true);
        userRepository.save(user);
        logger.info(user.getId() + " became a host");

        return mapper.map(user, BecomeHostDTO.class);
    }

    public UserEntity getByEmail(String userEmail) {
        return userRepository.getByEmail(userEmail).orElseThrow(() -> new NotFoundException("user not found"));
    }

    private SimpleMessageDTO generateMessageOnRegistration(RegisterDTO registerDTO) {
        SimpleMessageDTO dto = new SimpleMessageDTO();
        dto.setSendTo(registerDTO.getEmail());
        dto.setContent("Dear " + registerDTO.getFirstName() + " " + registerDTO.getLastName() + " welcome to Airbnb");
        dto.setSubject("Registration on Airbnb");
        return dto;
    }

    private void validateRegisterDto(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Passwords mismatch");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("This email already exists!");
        }

        LocalDate validateYear = LocalDate.now().minusYears(Utility.VALID_AGE);
        if (dto.getBirthdate().isAfter(validateYear)) {
            throw new BadRequestException("not a valid birthdate");
        }
    }
}
