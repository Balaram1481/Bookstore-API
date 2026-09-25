package com.bookstore.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter 
@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
public class LoginRequest {
    @NotBlank (message ="Email is Required")
    @Email(message="Ivalid email format")
    private String email;
    @NotBlank(message ="password is required")
    private String password;

}
