package com.bookstore.api.service.impl;

import com.bookstore.api.dto.JwtAuthResponse;
import com.bookstore.api.dto.LoginRequest;
import com.bookstore.api.dto.RegisterRequest;
import com.bookstore.api.entity.Role;
import com.bookstore.api.entity.User;
import com.bookstore.api.exception.BadRequestException;
import com.bookstore.api.repository.UserRepository;
import com.bookstore.api.dto.JwtTokenProvider;
import com.bookstore.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException(
                    "Email address is already registered!"
            );
        }

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail().toLowerCase())
                .password(
                        passwordEncoder.encode(
                                registerRequest.getPassword()
                        )
                )
                .role(
                        registerRequest.getRole() != null
                                ? registerRequest.getRole()
                                : Role.ROLE_CUSTOMER
                )
                .build();

        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public JwtAuthResponse login(LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail().toLowerCase(),
                                loginRequest.getPassword()
                        )
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String token =
                jwtTokenProvider.generateToken(authentication);

        User user = userRepository.findByEmail(
                        loginRequest.getEmail().toLowerCase())
                .orElseThrow(() ->
                        new BadRequestException("User Not Found"));

        return JwtAuthResponse.builder()
                .accesstoken(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}