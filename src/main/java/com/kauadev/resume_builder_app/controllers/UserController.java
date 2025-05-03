package com.kauadev.resume_builder_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kauadev.resume_builder_app.domain.user.User;
import com.kauadev.resume_builder_app.domain.user.UserDTO;
import com.kauadev.resume_builder_app.infra.ApiResponse;
import com.kauadev.resume_builder_app.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    private ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<List<User>>(
                HttpStatus.OK.value(), true,
                "Estes foram os usuários encontrados: ",
                users);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse<User>> getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        ApiResponse<User> response = new ApiResponse<User>(HttpStatus.OK.value(), true, "Usuário encontrado.", user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ApiResponse<User>> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO data) {
        User user = userService.updateUser(id, data);
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Dados do usuário atualizados.",
                user);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Usuário deletado", null);

        return ResponseEntity.ok().body(response);
    }

}
