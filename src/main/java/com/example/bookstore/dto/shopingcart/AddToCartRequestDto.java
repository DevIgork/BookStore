package com.example.bookstore.dto.shopingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddToCartRequestDto {
    @NotNull
    private Long bookId;
    @Positive(message = "value can't be 0 or lover")
    private int quantity;
}
