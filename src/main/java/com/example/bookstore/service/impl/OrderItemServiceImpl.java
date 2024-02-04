package com.example.bookstore.service.impl;

import com.example.bookstore.dto.order.OrderItemDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.OrderItemMapper;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.service.OrderItemService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDto> getAllItems(Long orderId) {
        List<OrderItem> allByOrderId = orderItemRepository.findAllByOrderId(orderId);
        return allByOrderId.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto getItem(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(orderId, itemId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order item by item id "
                        + itemId + " and order id" + orderId));
        return orderItemMapper.toDto(orderItem);
    }
}
