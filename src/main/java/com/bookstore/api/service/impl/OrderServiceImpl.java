package com.bookstore.api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.api.dto.OrderItemDto;
import com.bookstore.api.dto.OrderRequest;
import com.bookstore.api.dto.OrderResponse;
import com.bookstore.api.entity.Book;
import com.bookstore.api.entity.Order;
import com.bookstore.api.entity.OrderItem;
import com.bookstore.api.exception.BookAPIException;
import com.bookstore.api.exception.ResourceNotFound;
import com.bookstore.api.repository.BookRepository;
import com.bookstore.api.repository.OrderRepository;
import com.bookstore.api.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            BookRepository bookRepository) {

        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(
            OrderRequest orderRequest) {

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<OrderItem> orderItems =
                new ArrayList<>();

        Order order = new Order();

        order.setUserId(orderRequest.getUserId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        for (OrderItemDto itemDto :
                orderRequest.getItems()) {

            Book book = bookRepository
                    .findById(itemDto.getBookId())
                    .orElseThrow(() ->
                            new ResourceNotFound(
                                    "Book",
                                    "id",
                                    itemDto.getBookId()));

            if (book.getStockQuantity()
                    < itemDto.getQuantity()) {

                throw new BookAPIException(
                        HttpStatus.BAD_REQUEST,
                        "Insufficient stock for book: "
                                + book.getTitle());
            }

            book.setStockQuantity(
                    book.getStockQuantity()
                            - itemDto.getQuantity());

            bookRepository.save(book);

            OrderItem orderItem = new OrderItem();

            orderItem.setBook(book);
            orderItem.setQuantity(
                    itemDto.getQuantity());

            orderItem.setPrice(
                    book.getPrice());

            orderItem.setOrder(order);

            orderItems.add(orderItem);

            totalAmount = totalAmount.add(
                    book.getPrice().multiply(
                            BigDecimal.valueOf(
                                    itemDto.getQuantity())));
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder =
                orderRepository.save(order);

        OrderResponse response =
                new OrderResponse();

        response.setOrderId(savedOrder.getId());
        response.setUserId(savedOrder.getUserId());
        response.setOrderDate(savedOrder.getOrderDate());
        response.setTotalAmount(savedOrder.getTotalAmount());

        return response;
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(
            Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> {

                    OrderResponse response =
                            new OrderResponse();

                    response.setOrderId(order.getId());
                    response.setUserId(order.getUserId());
                    response.setTotalAmount(
                            order.getTotalAmount());
                    response.setOrderDate(
                            order.getOrderDate());

                    return response;
                })
                .toList();
    }
    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Order",
                                "orderId",
                                orderId));

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setUserId(order.getUserId());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());

        return response;
    }
    @Override
    @Transactional
    public OrderResponse updateOrderStatus(
            Long orderId,
            String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Order",
                                "orderId",
                                orderId));

        order.setStatus(status.toUpperCase());

        Order updatedOrder =
                orderRepository.save(order);

        OrderResponse response =
                new OrderResponse();

        response.setOrderId(updatedOrder.getId());
        response.setUserId(updatedOrder.getUserId());
        response.setTotalAmount(
                updatedOrder.getTotalAmount());
        response.setOrderDate(
                updatedOrder.getOrderDate());
        response.setStatus(
                updatedOrder.getStatus());

        return response;
    }
}