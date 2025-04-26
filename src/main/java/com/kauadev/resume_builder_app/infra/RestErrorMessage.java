package com.kauadev.resume_builder_app.infra;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// classe pra exibir os erros como JSON
@Getter
@Setter
@AllArgsConstructor
public class RestErrorMessage {

    private String message;
    private HttpStatus status;

}
