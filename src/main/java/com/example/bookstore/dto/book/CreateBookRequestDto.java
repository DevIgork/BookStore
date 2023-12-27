package com.example.bookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    @NotNull(message = " value can't be null")
    private String title;
    @NotNull(message = " value can't be null")
    private String author;
    @NotNull(message = " value can't be null")
    @Size(min = 10, max = 13,message = " value can't be lower than 10 or more then 13")
    @ISBN
    private String isbn;
    @NotNull(message = " value can't be null")
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
