package com.finalproject.airbnb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import com.finalproject.airbnb.model.DTOs.RegisterDTO;
import com.finalproject.airbnb.model.DTOs.SimpleMessageDTO;
import com.finalproject.airbnb.model.DTOs.UserWithoutPasswordDTO;
import com.finalproject.airbnb.model.entities.CountryCode;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.service.CountryCodeService;
import com.finalproject.airbnb.service.EmailService;
import com.finalproject.airbnb.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CountryCodeService countryCodeService;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

}