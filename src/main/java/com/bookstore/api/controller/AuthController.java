package com.bookstore.api.controller;

import com.bookstore.api.dto.ApiResponse;
import com.bookstore.api.dto.JwtAuthResponse;
import com.bookstore.api.dto.LoginRequest;
import com.bookstore.api.dto.RegisterRequest;
import com.bookstore.api.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest) {

        String message = userService.registerUser(registerRequest);

        return new ResponseEntity<>(
                new ApiResponse(true, message),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {

        JwtAuthResponse jwtAuthResponse =
                userService.login(loginRequest);

        return ResponseEntity.ok(jwtAuthResponse);
    }
}