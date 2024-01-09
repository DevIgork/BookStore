package com.example.bookstore.repository;

import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Shoppingcartrepositorytest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Sql(scripts = "classpath:database/shoppingCart/insert_into_shopping_carts.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/shoppingCart/delete_all_cart_data.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("""
                    find cart by user Id
                    """)
    void findByUserId_validData_Success() {
        ShoppingCart shoppingCart = new ShoppingCart();
        User user = new User();
        user.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setId(1L);
        Optional<ShoppingCart> expected = Optional.of(shoppingCart);
        Optional<ShoppingCart> actual = shoppingCartRepository.findByUserId(1L);
        Assertions.assertEquals(expected, actual);
    }
}
