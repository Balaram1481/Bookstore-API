package com.bookstore.api.service;

import java.util.List;

import com.bookstore.api.dto.OrderRequest;
import com.bookstore.api.dto.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest);

    List<OrderResponse> getOrdersByUserId(Long userId);

    OrderResponse getOrderById(Long orderId);

    OrderResponse updateOrderStatus(
            Long orderId,
            String status);
}