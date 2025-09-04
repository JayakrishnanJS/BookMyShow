package com.jkdev.bookmyshow;

import com.jkdev.bookmyshow.controller.UserController;
import com.jkdev.bookmyshow.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookMyShowApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSignUpFunctionality() {
        UserSignUpRequest request = new UserSignUpRequest();
        request.setName("John Doe");
        request.setEmail("jk@x.com");
        request.setPassword("12345");

        UserSignUpResponse response = userController.userSignUp(request);

        System.out.println(response.getUserId());
    }

    @Test
    public void testLoginFunctionality() {
        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("jk@x.com");
        request.setPassword("12345");

        UserLoginResponse response = userController.userLogin(request);

        if(response.getResponseStatus() == ResponseStatus.SUCCESS) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Login Failed");
        }
    }
}
