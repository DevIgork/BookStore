package com.example.bookstore.service.impl;

import com.example.bookstore.dto.shopingCart.AddToCartRequestDto;
import com.example.bookstore.dto.shopingCart.ShoppingCartDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopingCartServiceIml implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    @Transactional
    public ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long userId) {
        CartItem cartItem = new CartItem();
        Long bookId = requestDto.getBookId();
        User user = userRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id :"
                        + userId));
        ShoppingCart shoppingCartFromDB = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setUser(user);
                    cartRepository.save(shoppingCart);
                    return shoppingCart;
        });
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setBook(
                bookRepository.findById(
                        requestDto.getBookId())
                        .orElseThrow(() -> new EntityNotFoundException("Can't find book by id :"
                                + bookId)
                )
        );
        cartItem.setShoppingCart(shoppingCartFromDB);
        cartItemRepository.save(cartItem);
        return null;
    }
}
