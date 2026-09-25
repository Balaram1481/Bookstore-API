package com.bookstore.api.dto;

import lombok.*;
@Getter @Setter 
@AllArgsConstructor
@NoArgsConstructor 
public class ApiResponse {
    private boolean success;
    private String message;

}
