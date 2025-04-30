package com.kauadev.resume_builder_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kauadev.resume_builder_app.domain.user.LoginDTO;
import com.kauadev.resume_builder_app.domain.user.User;
import com.kauadev.resume_builder_app.domain.user.UserDTO;
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

    @PostMapping("/register")
    private ResponseEntity<ApiResponse<User>> register(@RequestBody UserDTO userDTO) {
        String password_hash = passwordEncoder.encode(userDTO.password());

        User userToBeSaved = new User(userDTO.username(), password_hash, userDTO.role());
        User user = userRepository.save(userToBeSaved);

        ApiResponse<User> response = new ApiResponse<User>(HttpStatus.OK.value(), true,
                "Usuário cadastrado com sucesso!", user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    private ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                loginDTO.username(), loginDTO.password());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) authentication);

        ApiResponse<String> response = new ApiResponse<String>(HttpStatus.OK.value(), true,
                "Usuário logado com sucesso", token);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
