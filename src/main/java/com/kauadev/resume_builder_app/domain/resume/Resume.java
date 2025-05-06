package com.kauadev.resume_builder_app.domain.resume;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.kauadev.resume_builder_app.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String file_path;
    private String position;

    @CreationTimestamp
    private LocalDateTime created_at;

    @OneToOne
    private User user;

    public Resume(String file_path, String position) {
        this.file_path = file_path;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return file_path;
    }

    public void setFilePath(String file_path) {
        this.file_path = file_path;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
