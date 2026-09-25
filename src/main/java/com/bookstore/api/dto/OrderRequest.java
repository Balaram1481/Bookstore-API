package com.bookstore.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {

    private Long userId;

    private List<OrderItemDto> items;
}