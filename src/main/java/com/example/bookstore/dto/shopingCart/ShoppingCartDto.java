package com.example.bookstore.dto.shopingCart;

import lombok.Data;
@Data
public class ShoppingCartDto {
    private long id;
    private long userId;
    private List<CartItemDto> cartItem;
}
