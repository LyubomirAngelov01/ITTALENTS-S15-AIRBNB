package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController

public class UserController extends AbstractController {


    @Autowired
    private UserService userService;


    @PostMapping("/users/signup")
    public UserWithoutPasswordDTO register(@Valid @RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/users/login")
    public TokenDTO login(@RequestBody LoginDTO dto) {
        TokenDTO responseDto = userService.login(dto);
        return responseDto;
    }


    @PutMapping("/users/profile/info")
    @SecurityRequirement(name = "JWT token")
    public UserWithoutPasswordDTO editProfile(@Valid @RequestBody EditProfileInfoDTO dto, HttpServletRequest request) {
        int id = extractUserIdFromToken(request);
        return userService.editProfileInfo(dto, id);
    }

    @PutMapping("/users/profile/loginCredentials")
    @SecurityRequirement(name = "JWT token")
    public UserWithoutPasswordDTO editLoginCredentials(@Valid @RequestBody EditLoginCredentialsDTO dto, HttpServletRequest request) {
        int id = extractUserIdFromToken(request);
        return userService.editLoginCredentials(id, dto);
    }

    @DeleteMapping("/users")
    @SecurityRequirement(name = "JWT token")
    public void deleteAccount(HttpServletRequest request) {
        userService.deleteAccount(extractUserIdFromToken(request));
    }

    @GetMapping("/users/{id}")
    @SecurityRequirement(name = "JWT token")
    public UserWithoutPasswordDTO checkUserProfile(@PathVariable int id) {
        return userService.checkProfile(id);
    }

    @GetMapping("/users/trips")
    @SecurityRequirement(name = "JWT token")
    public List<TripDTO> checkTrips(HttpServletRequest request, Pageable pageable) {
        int id = extractUserIdFromToken(request);
        return userService.listAllTrips(id,pageable);
    }

    @PutMapping("/users/become_host")
    @SecurityRequirement(name = "JWT token")
    public BecomeHostDTO becomeHost(HttpServletRequest request) {
        int id = extractUserIdFromToken(request);
        return userService.setHostStatus(id);
    }
}
