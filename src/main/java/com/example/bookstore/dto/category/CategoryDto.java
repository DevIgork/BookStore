package com.example.bookstore.dto.category;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
