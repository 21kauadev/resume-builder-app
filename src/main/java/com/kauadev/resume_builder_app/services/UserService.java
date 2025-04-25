package com.kauadev.resume_builder_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kauadev.resume_builder_app.domain.user.User;
import com.kauadev.resume_builder_app.domain.user.exceptions.UserNotFoundException;
import com.kauadev.resume_builder_app.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // evitando o uso de this se for desnecessário! melhora a legibilidade

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }

    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return user;
    }

}
