package com.example.bookstore.service;

import com.example.bookstore.dto.shopingCart.AddToCartRequestDto;
import com.example.bookstore.dto.shopingCart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long userId);
}
