package com.example.bookstore.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.shopingcart.AddToCartRequestDto;
import com.example.bookstore.dto.shopingcart.CartItemDto;
import com.example.bookstore.dto.shopingcart.ShoppingCartDto;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.impl.ShoppingCartServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {
    private static final Long CLEAN_CODER_ID = 1L;
    private static final BigDecimal CLEAN_CODER_PRICE = new BigDecimal("456.00");
    private static final String CLEAN_CODER_TITLE =
            "The Clean Coder: A Code of Conduct for Professional Programmers";
    private static final String CLEAN_CODER_AUTHOR = "Robert C. Martin";
    private static final String CLEAN_CODER_ISBN =
            "9789669820229";
    private static final String CLEAN_CODER_DESCRIPTION =
            "A Code of Conduct for Professional Programmers,"
                    + " legendary software expert Robert C. Martin";
    private static final String CLEAN_CODER_COVER_IMAGE = "https://balka-book.com/files/2023/07_06/12_56/u_files_store_5_7.jpg";
    private static Book cleanCoder;
    @InjectMocks
    private ShoppingCartServiceImpl cartService;
    @Mock
    private ShoppingCartRepository cartRepository;
    @Mock
    private ShoppingCartMapper cartMapper;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartItemRepository cartItemRepository;

    @BeforeAll
    public static void beforeAll() {
        cleanCoder = new Book();
        cleanCoder.setTitle(CLEAN_CODER_TITLE);
        cleanCoder.setPrice(CLEAN_CODER_PRICE);
        cleanCoder.setId(CLEAN_CODER_ID);
        cleanCoder.setDescription(CLEAN_CODER_DESCRIPTION);
        cleanCoder.setIsbn(CLEAN_CODER_ISBN);
        cleanCoder.setAuthor(CLEAN_CODER_AUTHOR);
        cleanCoder.setCoverImage(CLEAN_CODER_COVER_IMAGE);
    }

    @Test
    @DisplayName("""
            verify addToCart() method work
            """)
    public void addToCart_ValidData_Success() {
        AddToCartRequestDto requestDto = new AddToCartRequestDto()
                .setBookId(1L)
                .setQuantity(2);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(requestDto.getQuantity());
        ShoppingCart shoppingCart = new ShoppingCart();
        User user = new User();
        user.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setId(1L);
        cartItem.setBook(cleanCoder);
        cartItem.setShoppingCart(shoppingCart);
        CartItemDto cartItemDto = new CartItemDto()
                .setBookId(cleanCoder.getId())
                .setBookTitle(cleanCoder.getTitle())
                .setQuantity(1);
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto()
                .setCartItem(List.of(cartItemDto))
                .setUserId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(cleanCoder));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(shoppingCart));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
        when(cartMapper.toDto(shoppingCart)).thenReturn(shoppingCartDto);
        ShoppingCartDto actual = cartService.addToCart(requestDto, 1L);
        assertThat(actual).isEqualTo(shoppingCartDto);
        verifyNoMoreInteractions(bookRepository, cartRepository, cartItemRepository, cartMapper);
    }
}
