package com.kauadev.resume_builder_app.domain.user.exceptions;

public class CommonUserCanNotDeleteUsersException extends RuntimeException {

    public CommonUserCanNotDeleteUsersException() {
        super("Usuário comum não pode deletar sua conta ou a conta de outros usuários.");
    }

    public CommonUserCanNotDeleteUsersException(String msg) {
        super(msg);
    }

}
