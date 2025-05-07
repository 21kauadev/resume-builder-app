package com.kauadev.resume_builder_app.domain.resume.exceptions;

public class AdminCanNotHaveResumesException extends RuntimeException {

    public AdminCanNotHaveResumesException(String msg) {
        super(msg);
    }

    public AdminCanNotHaveResumesException() {
        super("Administradores / RH não são permitidos a acessar seus próprios currículos, pois não têm currículo.");
    }

}
