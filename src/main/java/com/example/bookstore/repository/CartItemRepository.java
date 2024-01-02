package com.example.bookstore.repository;

import com.example.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
