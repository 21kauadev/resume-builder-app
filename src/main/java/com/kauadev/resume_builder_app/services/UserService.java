package com.kauadev.resume_builder_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kauadev.resume_builder_app.domain.user.User;
import com.kauadev.resume_builder_app.domain.user.UserDTO;
import com.kauadev.resume_builder_app.domain.user.exceptions.CommonUserCanNotDeleteUsersException;
import com.kauadev.resume_builder_app.domain.user.exceptions.UserNotFoundException;
import com.kauadev.resume_builder_app.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // evitando o uso de this se for desnecessário! melhora a legibilidade

    private User getAuthenticationPrincipalUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }

    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return user;
    }

    // update simples temporário.
    public User updateUser(Long id, UserDTO data) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        // hash da senha
        String newPassword = passwordEncoder.encode(data.password());

        // salvando novos dados
        user.setUsername(data.username());
        user.setPassword(newPassword);

        // salvando user novamente
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User loggedUser = getAuthenticationPrincipalUser();
        if (!loggedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new CommonUserCanNotDeleteUsersException();
        }

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }

}
