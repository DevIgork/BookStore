package com.example.bookstore.service;

import com.example.bookstore.dto.shopingcart.AddToCartRequestDto;
import com.example.bookstore.dto.shopingcart.CartItemDto;
import com.example.bookstore.dto.shopingcart.ShoppingCartDto;
import com.example.bookstore.dto.shopingcart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long userId);

    ShoppingCartDto get(Long userId);

    CartItemDto update(Long cartItemId, UpdateCartItemRequestDto requestDto);
}
