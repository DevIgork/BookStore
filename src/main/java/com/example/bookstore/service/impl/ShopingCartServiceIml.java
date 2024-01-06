package com.example.bookstore.service.impl;

import com.example.bookstore.dto.shopingcart.AddToCartRequestDto;
import com.example.bookstore.dto.shopingcart.CartItemDto;
import com.example.bookstore.dto.shopingcart.ShoppingCartDto;
import com.example.bookstore.dto.shopingcart.UpdateCartItemRequestDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.CartItemMapper;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopingCartServiceIml implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long userId) {
        CartItem cartItem = new CartItem();
        Long bookId = requestDto.getBookId();
        Book book = bookRepository.findById(
                bookId).orElseThrow(() -> new EntityNotFoundException("Can't find book by id :"
                + bookId)
        );
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
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCartFromDB);
        cartItemRepository.save(cartItem);
        shoppingCartFromDB.getCartItem().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCartFromDB);
    }

    @Override
    public ShoppingCartDto get(Long userId) {
        return shoppingCartMapper.toDto(
                cartRepository.findByUserId(userId)
                        .orElseThrow(()
                                -> new EntityNotFoundException("can't find user By id" + userId))
        );
    }

    @Override
    @Transactional
    public CartItemDto update(Long cartItemId, UpdateCartItemRequestDto requestDto) {
        Optional<CartItem> carItemOp = cartItemRepository.findById(cartItemId);
        CartItem cartItem = carItemOp
                .orElseThrow(() -> new EntityNotFoundException("Can't find cartItem by id "
                        + cartItemId));
        cartItem.setQuantity(requestDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }
}
