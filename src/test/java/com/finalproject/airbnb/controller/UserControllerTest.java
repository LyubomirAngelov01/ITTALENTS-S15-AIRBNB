package com.finalproject.airbnb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import com.finalproject.airbnb.model.DTOs.RegisterDTO;
import com.finalproject.airbnb.model.DTOs.SimpleMessageDTO;
import com.finalproject.airbnb.model.DTOs.UserWithoutPasswordDTO;
import com.finalproject.airbnb.model.entities.CountryCode;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.service.CountryCodeService;
import com.finalproject.airbnb.service.EmailService;
import com.finalproject.airbnb.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterDTO registerDto = new RegisterDTO();
        registerDto.setFirstName("John");
        registerDto.setLastName("Doe");
        registerDto.setBirthdate(LocalDate.of(1990, 1, 1));
        registerDto.setEmail("john.doe@example.com");
        registerDto.setPassword("P@ssword1!");
        registerDto.setConfirmPassword("P@ssword1!");
        registerDto.setCountryCode(1);
        registerDto.setPhoneNumber("1234567890");

        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setEmail("john.doe@example.com");
        user.setPassword("hashedPassword");
        user.setCountryCode(new CountryCode());
        user.setPhoneNumber("1234567890");

        when(userRepository.save(any(User.class))).thenReturn(user);

        when(countryCodeService.findById(1)).thenReturn(new CountryCode());

        when(encoder.encode("P@ssword1!")).thenReturn("hashedPassword");

        doNothing().when(emailService).sendEmail(any(SimpleMessageDTO.class));

        UserWithoutPasswordDTO userDto = userService.register(registerDto);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.firstName);
        assertEquals(user.getLastName(), userDto.lastName);

        verify(userRepository, times(1)).save(any(User.class));
        verify(countryCodeService, times(1)).findById(1);

        verify(encoder, times(1)).encode("P@ssword1!");
        verify(emailService, times(1)).sendEmail(any(SimpleMessageDTO.class));
    }
}