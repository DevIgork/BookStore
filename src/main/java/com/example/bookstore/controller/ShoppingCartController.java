package com.example.bookstore.controller;

import com.example.bookstore.dto.shopingCart.AddToCartRequestDto;
import com.example.bookstore.dto.shopingCart.ShoppingCartDto;
import com.example.bookstore.model.User;
import com.example.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    @PostMapping
    public ShoppingCartDto addToCart(
            @RequestBody AddToCartRequestDto requestDto,
            Authentication authentication
            ) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.addToCart(requestDto, user.getId());
    }

}
