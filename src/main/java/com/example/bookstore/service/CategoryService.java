package com.example.bookstore.service;

import com.example.bookstore.dto.category.CategoryDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> getAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto createCategory(CreateCategoryRequestDto categoryRequestDto);

    void deleteById(Long id);

    CategoryDto update(Long id, CategoryDto categoryDto);
}
