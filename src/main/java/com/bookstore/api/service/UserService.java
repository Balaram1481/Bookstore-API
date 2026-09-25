package com.bookstore.api.service;
import com.bookstore.api.dto.JwtAuthResponse;
import com.bookstore.api.dto.LoginRequest;
import com.bookstore.api.dto.RegisterRequest;
public interface UserService{
    String registerUser(RegisterRequest registerRequest);
    JwtAuthResponse login(LoginRequest loginRequest);

}
