package com.example.bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.bookstore.dto.shopingcart.AddToCartRequestDto;
import com.example.bookstore.dto.shopingcart.CartItemDto;
import com.example.bookstore.dto.shopingcart.ShoppingCartDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;
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
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(3L);
        user.setEmail("coolemail@gmai.com");
        user.setPassword("hardpassword");
        Role role = new Role();
        role.setId(3L);
        role.setRole(Role.RoleName.USER);
        user.setRoles(Set.of(role));
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @BeforeAll
    public static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        cleanCoder = new Book();
        cleanCoder.setTitle(CLEAN_CODER_TITLE);
        cleanCoder.setPrice(CLEAN_CODER_PRICE);
        cleanCoder.setId(CLEAN_CODER_ID);
        cleanCoder.setDescription(CLEAN_CODER_DESCRIPTION);
        cleanCoder.setIsbn(CLEAN_CODER_ISBN);
        cleanCoder.setAuthor(CLEAN_CODER_AUTHOR);
        cleanCoder.setCoverImage(CLEAN_CODER_COVER_IMAGE);
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("""
            add to cart
            """)
    public void addToCart_ValidData_Success() throws Exception {
        AddToCartRequestDto requestDto = new AddToCartRequestDto()
                .setQuantity(20)
                .setBookId(1L);

        CartItemDto cartItemDto = new CartItemDto()
                .setId(1L)
                .setBookTitle(cleanCoder.getTitle())
                .setBookId(requestDto.getBookId())
                .setQuantity(requestDto.getQuantity());
        ShoppingCartDto expected = new ShoppingCartDto()
                .setId(1L)
                .setUserId(2L)
                .setCartItem(List.of(cartItemDto));
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                post("/api/cart")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        ShoppingCartDto actual = objectMapper
                .readValue(
                        result
                        .getResponse()
                        .getContentAsString(),
                        ShoppingCartDto.class
        );
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

}
