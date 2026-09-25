package com.bookstore.api.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long bookId;
    private Integer quantity;
}