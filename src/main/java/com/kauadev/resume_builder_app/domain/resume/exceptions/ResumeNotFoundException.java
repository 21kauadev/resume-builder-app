package com.kauadev.resume_builder_app.domain.resume.exceptions;

public class ResumeNotFoundException extends RuntimeException {

    public ResumeNotFoundException(String msg) {
        super(msg);
    }

    public ResumeNotFoundException() {
        super("Currículo não encontrado.");
    }

}
