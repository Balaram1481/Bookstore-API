package com.bookstore.api.dto;
import com.bookstore.api.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class RegisterRequest {
    @NotBlank(message = "Name is Required")
    @Size(min=2,max=100,message="name must be between 2 and 100 characters")
    private String name;
    @NotBlank(message = "Email is Required")
    @Email(message="Invalid Email ID formate")
    private String email;
    @NotBlank(message = "password is Required")
    @Size(min=6,message="password must be atleast 6 characters long")
    private String password;
    private Role role;


}
