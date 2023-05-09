package com.example.career;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class CareerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("")
                        .allowedMethods("GET", "POST");            }
        };
    }
}