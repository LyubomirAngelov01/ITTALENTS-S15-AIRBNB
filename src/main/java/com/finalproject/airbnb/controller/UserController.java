package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.*;

import com.finalproject.airbnb.service.JwtService;
import com.finalproject.airbnb.service.UserService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController extends  AbstractController{



    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/users/signup")
    public UserWithoutPasswordDTO register(@Valid @RequestBody RegisterDTO dto){
        return userService.register(dto);
    }

    @PostMapping("/users/login")
    public TokenDTO login(@RequestBody LoginDTO dto){
        TokenDTO responseDto = userService.login(dto);
//        session.setAttribute(Utility.LOGGED,true);
//        session.setAttribute(Utility.LOGGED_ID,responseDto.getId());
        return responseDto;
    }

    @PostMapping("/users/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }

    @PutMapping("/users/profile/info")
    public UserWithoutPasswordDTO editProfile(@Valid @RequestBody EditProfileInfoDTO dto, HttpServletRequest request){

        int id = extractUserIdFromToken(request);
        return userService.editProfileInfo(dto,id);
    }

    @PutMapping("/users/profile/loginCredentials")
    public UserWithoutPasswordDTO editLoginCredentials(@Valid @RequestBody EditLoginCredentialsDTO dto, HttpServletRequest request){
        int id = extractUserIdFromToken(request);
        return userService.editLoginCredentials(id,dto);
    }
    @DeleteMapping("/users")
    public void deleteAccount(HttpServletRequest request){
        userService.deleteAccount(extractUserIdFromToken(request));
    }
    @GetMapping("/users/{id}")
    public UserWithoutPasswordDTO checkUserProfile(@PathVariable int id){
        return userService.checkProfile(id);
    }
    @GetMapping("/users/trips")
    public List<TripDTO> checkTrips(HttpServletRequest request){
//        int userId = getLoggedId(session);
        int id = extractUserIdFromToken(request);
        return userService.listAllTrips(id);
    }
    @PutMapping("/users/become_host")
    public BecomeHostDTO becomeHost(HttpServletRequest request){
        int id = extractUserIdFromToken(request);
        return userService.setHostStatus(id);
    }


}
