package com.kauadev.resume_builder_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kauadev.resume_builder_app.domain.user.User;
import com.kauadev.resume_builder_app.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = this.userRepository.findAll();

        return users;
    }

}
