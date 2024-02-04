package com.example.bookstore.dto.shopingcart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @Positive(message = "value can't be 0 or lover")
    private int quantity;
}
