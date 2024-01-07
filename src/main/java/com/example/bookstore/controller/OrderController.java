package com.example.bookstore.controller;

import com.example.bookstore.dto.order.AddOrderRequestDto;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.order.OrderItemDto;
import com.example.bookstore.dto.order.UpdateOrderStatusDto;
import com.example.bookstore.model.User;
import com.example.bookstore.service.OrderItemService;
import com.example.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing Orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Operation(summary = "Create order", description = "create an order")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public OrderDto createOrder(
            @RequestBody @Valid AddOrderRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.create(requestDto, user.getId());
    }

    @Operation(summary = "Get orders", description = "get a list of all orders of user")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<OrderDto> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAll(user.getId());
    }

    @Operation(summary = "Patch status", description = "change status of order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("{id}")
    public OrderDto updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusDto updateOrderStatusDto
    ) {
        return orderService.update(updateOrderStatusDto, id);
    }

    @Operation(summary = "Get OrderItems", description = "get orderItems")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getAllItems(@PathVariable Long orderId) {
        return orderItemService.getAllItems(orderId);
    }

    @Operation(summary = "Get OrderItem in order", description = "Get OrderItem in order")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("{orderId}/items/{itemId}")
    public OrderItemDto getItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderItemService.getItem(orderId, itemId);
    }

}
