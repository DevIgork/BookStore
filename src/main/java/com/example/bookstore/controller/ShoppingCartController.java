package com.example.bookstore.controller;

import com.example.bookstore.dto.shopingcart.AddToCartRequestDto;
import com.example.bookstore.dto.shopingcart.CartItemDto;
import com.example.bookstore.dto.shopingcart.ShoppingCartDto;
import com.example.bookstore.dto.shopingcart.UpdateCartItemRequestDto;
import com.example.bookstore.model.User;
import com.example.bookstore.service.CartItemService;
import com.example.bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Add item", description = "add item to cart "
            + "if cart not present create cart")
    @PostMapping
    public ShoppingCartDto addToCart(
            @RequestBody @Valid AddToCartRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addToCart(requestDto, user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get cart", description = "Get a user cart")
    @GetMapping
    public ShoppingCartDto getCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.get(user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "update cartItem", description = "update cart item by Id")
    @PutMapping("/cart-items/{cartItemId}")
    public CartItemDto updateCart(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequestDto requestDto
    ) {
        return shoppingCartService.update(cartItemId, requestDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Delete cartItem", description = "delete cart item by Id")
    @DeleteMapping("/cart-items/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.delete(cartItemId);
    }
}
