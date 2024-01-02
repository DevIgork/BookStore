package com.example.bookstore.dto.shopingCart;

import lombok.Data;

@Data
public class AddToCartRequestDto {
    private Long bookId;
    private int quantity;
}
