package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.LoginDTO;
import com.finalproject.airbnb.model.DTOs.RegisterDTO;
import com.finalproject.airbnb.model.DTOs.UserWithoutPasswordDTO;
import com.finalproject.airbnb.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends  AbstractController{

    @Autowired
    private UserService userService;
    @PostMapping("/users/signup")
    public UserWithoutPasswordDTO register( @Valid @RequestBody RegisterDTO dto){
        return userService.register(dto);
    }

    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody LoginDTO dto, HttpSession session){
        UserWithoutPasswordDTO responseDto = userService.login(dto);
        session.setAttribute(Utility.LOGGED,true);
        session.
        session.setAttribute(Utility.LOGGED_ID,responseDto.getId());
        return responseDto;
    }

    @PostMapping("/users/logout")
    public UserLogoutDTO logout(){

    }


}
