package com.example.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotNull(message = " value can't be null")
    @NotBlank(message = "value can't be blank")
    private String title;
    @NotNull(message = " value can't be null")
    @NotBlank(message = "value can't be blank")
    private String author;
    @NotNull(message = " value can't be null")
    @NotBlank(message = "value can't be blank")
    @Pattern(regexp = "^(10|13)$", message = "value size must be 10 or 13")
    private String isbn;
    @NotNull(message = " value can't be null")
    @NotBlank(message = "value can't be blank")
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
