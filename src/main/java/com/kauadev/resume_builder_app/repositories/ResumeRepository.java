package com.kauadev.resume_builder_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauadev.resume_builder_app.domain.resume.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

}
