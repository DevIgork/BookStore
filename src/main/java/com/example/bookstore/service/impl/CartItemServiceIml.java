package com.example.bookstore.service.impl;

import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceIml implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
