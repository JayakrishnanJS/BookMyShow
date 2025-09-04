package com.jkdev.bookmyshow.controller;

import com.jkdev.bookmyshow.dto.*;
import com.jkdev.bookmyshow.model.User;
import com.jkdev.bookmyshow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserSignUpResponse userSignUp(UserSignUpRequest userSignUpRequest) {
        UserSignUpResponse response = new UserSignUpResponse();
        User user = userService.userSignUp(userSignUpRequest.getName(), userSignUpRequest.getEmail(), userSignUpRequest.getPassword());
        response.setUserId(user.getId());
        response.setMessage("User registration successful");
        response.setResponseStatus(ResponseStatus.SUCCESS);
        return response;
    }

    public UserLoginResponse userLogin(UserLoginRequest userLoginRequest) {
        UserLoginResponse response = new UserLoginResponse();
        try {
            User user = userService.userLogin(userLoginRequest.getEmail(), userLoginRequest.getPassword());
            response.setMessage("User login successful");
            response.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
