package com.example.bookstore.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItem;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private String status;
}
