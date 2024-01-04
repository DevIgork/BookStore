package com.example.bookstore.service;

import com.example.bookstore.dto.order.AddOrderRequestDto;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.order.UpdateOrderStatusDto;

import java.util.List;

public interface OrderService {
    OrderDto create(AddOrderRequestDto requestDto, Long userId);

    List<OrderDto> getAll(Long userId);

    OrderDto update(UpdateOrderStatusDto updateOrderStatusDto, Long id);
}
