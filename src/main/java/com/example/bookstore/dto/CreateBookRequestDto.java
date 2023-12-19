package com.example.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    @NotNull(message = "value can't be null")
    @NotBlank(message = "value can't be blank")
    private String title;
    @NotNull(message = "value can't be null")
    @NotBlank(message = "value can't be blank")
    private String author;
    @NotNull(message = " value can't be null")
    @NotBlank(message = "value can't be blank")
    @ISBN(message = "value must be isbn")
    private String isbn;
    @NotNull(message = " value can't be null")
    @NotBlank(message = "value can't be blank")
    @Positive(message = "value can't be negative")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
