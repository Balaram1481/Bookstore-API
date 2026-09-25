package com.bookstore.api.dto;
import com.bookstore.api.entity.Role;

import lombok.*;
@Setter 
@Getter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
public class JwtAuthResponse {
    private String accesstoken;
    @Builder.Default
    private String tokenType="Bearer";
    private String email;
    private String role;

}
