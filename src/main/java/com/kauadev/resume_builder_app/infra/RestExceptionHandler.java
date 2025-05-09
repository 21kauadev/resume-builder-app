package com.kauadev.resume_builder_app.infra;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kauadev.resume_builder_app.domain.user.exceptions.CommonUserCanNotDeleteUsersException;
import com.kauadev.resume_builder_app.domain.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ UserNotFoundException.class })
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
    }

    @ExceptionHandler({ CommonUserCanNotDeleteUsersException.class })
    private ResponseEntity<RestErrorMessage> commonUserCanNotDeleteOtherUsersHandler(
            CommonUserCanNotDeleteUsersException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(exception.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED.value());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(restErrorMessage);
    }

    @ExceptionHandler({ IOException.class })
    private ResponseEntity<RestErrorMessage> IOExceptionHandler(IOException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler({ JWTCreationException.class })
    private ResponseEntity<RestErrorMessage> jwtCreationExceptionHandler(JWTCreationException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler({ JWTVerificationException.class })
    private ResponseEntity<RestErrorMessage> jwtVerificationExceptionHandler(JWTVerificationException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler({ JWTDecodeException.class })
    private ResponseEntity<RestErrorMessage> jwtDecodeExceptionHandler(JWTDecodeException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler({ AuthenticationException.class })
    private ResponseEntity<RestErrorMessage> authenticationExceptionHandler(AuthenticationException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler({ Exception.class })
    private ResponseEntity<RestErrorMessage> generalExceptionHandler(Exception exception) {
        logger.error("Exception capturada: ", exception);
        RestErrorMessage restErrorMessage = new RestErrorMessage(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

}
