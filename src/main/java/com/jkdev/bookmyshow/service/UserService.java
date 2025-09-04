package com.jkdev.bookmyshow.service;

import com.jkdev.bookmyshow.exception.PasswordNotMatchedException;
import com.jkdev.bookmyshow.exception.UserNotFoundException;
import com.jkdev.bookmyshow.model.User;
import com.jkdev.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    private String sessionToken;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, SessionService sessionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
    }
    // 1. Check if user with the given email already exists
    // 2. If it exists, ask user to login instead of signing up
    // 3. If not, create a new User object
    // 4. save it to the database

    public User userSignUp(String name, String email, String password) {

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User userLogin(String email, String password) throws UserNotFoundException, PasswordNotMatchedException {

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new UserNotFoundException("User with email: " +email+ " not found"));

        // compare the password
        if(passwordEncoder.matches(password, user.getPassword())) {
            sessionToken = sessionService.createSession(user.getId());
            return user;
        } else {
            throw new PasswordNotMatchedException("Invalid password");
        }
    }

    public boolean isUserLoggedIn() {
        return sessionService.isValid(sessionToken);
    }
}
