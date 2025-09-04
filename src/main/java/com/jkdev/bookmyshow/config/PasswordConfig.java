package com.jkdev.bookmyshow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*
    - A Spring bean is any object whose lifecycle (creation, dependency injection, configuration, and destruction) is managed
      by the Spring IoC container. You get inversion of control: you ask Spring for dependencies instead of instantiating them manually.
    - @Bean registers the returned object as a Spring-managed bean.
    - @Bean marks a method inside a @Configuration class whose return value is registered in the Spring ApplicationContext.
      Default scope is singleton, so only one BCryptPasswordEncoder instance is created and reused (no extra singleton code needed).
    - Without this bean, Spring cannot inject the encoder into UserService unless another configuration auto‑provides it.
*/