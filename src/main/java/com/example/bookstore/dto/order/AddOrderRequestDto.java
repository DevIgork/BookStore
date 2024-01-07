package com.example.bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddOrderRequestDto {
    @Length(max = 255, message = "value max size is 255")
    @NotBlank(message = "value can't be blank")
    private String shippingAddress;
}
