package com.kauadev.resume_builder_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kauadev.resume_builder_app.repositories.UserRepository;

// tinha esquecido a annotation service e sofri pra achar o motivo de tá dando erro.
// outra vez, esqueci de pôr o implements UserDetailsService, também!
// tem q ficar de olho! 
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        if (username == null) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }

        return this.userRepository.findByUsername(username);
    }
}
