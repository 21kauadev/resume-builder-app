package com.kauadev.resume_builder_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kauadev.resume_builder_app.domain.user.User;
import com.kauadev.resume_builder_app.infra.ApiResponse;
import com.kauadev.resume_builder_app.infra.security.TokenService;
import com.kauadev.resume_builder_app.repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    // @PostMapping("/register")
    // private ResponseEntity<ApiResponse<User>> register() {

    // }
}
