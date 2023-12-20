package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateBookRequestDto {
    @Length(max = 255)
    @NotNull(message = "value can't be null")
    private String title;
    @Length(max = 255)
    @NotNull(message = " value can't be null")
    private String author;
    @NotNull(message = " value can't be null")
    @ISBN(message = "value must be isbn")
    private String isbn;
    @NotNull(message = " value can't be null")
    @Positive(message = "value can't be negative")
    private BigDecimal price;
    @Length(max = 255)
    private String description;
    @Length(max = 255)
    private String coverImage;
}
