package com.kauadev.resume_builder_app.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// classe para padronizar os retornos dos controllers
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {

    private Integer status;
    private boolean success;
    private String message;
    private T data;

}
