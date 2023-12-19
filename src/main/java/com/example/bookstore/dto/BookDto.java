package com.example.bookstore.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookDto {
    @NotNull(message = " value can't be null")
    @NotBlank(message = "value can't be blank")
    @Positive(message = "value can't be negative")
    private Long id;
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
