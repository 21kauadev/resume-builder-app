package com.kauadev.resume_builder_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauadev.resume_builder_app.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // adicionar o userDetails depois no setup do spring secuirty
}
