package com.example.bookstore.service;

import com.example.bookstore.dto.order.OrderItemDto;
import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> getAllItems(Long orderId);

    OrderItemDto getItem(Long orderId, Long itemId);
}
