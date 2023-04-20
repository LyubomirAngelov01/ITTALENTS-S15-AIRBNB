package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.*;

import com.finalproject.airbnb.service.UserService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController extends  AbstractController{



    private final UserService userService;

    @PostMapping("/users/signup")
    public UserWithoutPasswordDTO register(@Valid @RequestBody RegisterDTO dto){
        return userService.register(dto);
    }

    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody LoginDTO dto, HttpSession session){
        UserWithoutPasswordDTO responseDto = userService.login(dto);
        session.setAttribute(Utility.LOGGED,true);
        session.setAttribute(Utility.LOGGED_ID,responseDto.getId());
        return responseDto;
    }

    @PostMapping("/users/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }

    @PutMapping("/users/profile/info")
    public UserWithoutPasswordDTO editProfile(@Valid @RequestBody EditProfileInfoDTO dto, HttpSession session){

        int id = getLoggedId(session);
        return userService.editProfileInfo(dto,id);
    }

    @PutMapping("/users/profile/loginCredentials")
    public UserWithoutPasswordDTO editLoginCredentials(@Valid @RequestBody EditLoginCredentialsDTO dto, HttpSession session){
        int id = getLoggedId(session);
        return userService.editLoginCredentials(id,dto);
    }
    @DeleteMapping("/users")
    public void deleteAccount( HttpSession session){
        userService.deleteAccount(getLoggedId(session));
    }
    @GetMapping("/users/{id}")
    public UserWithoutPasswordDTO checkUserProfile(@PathVariable int id){
        return userService.checkProfile(id);
    }
    @GetMapping("/users/trips")
    public List<TripDTO> checkTrips(HttpSession session){
        int userId = getLoggedId(session);
        return userService.listAllTrips(userId);
    }
    @PutMapping("/users/become_host")
    public BecomeHostDTO becomeHost(HttpSession session){
        int id = getLoggedId(session);
        return userService.setHostStatus(id);
    }


}
