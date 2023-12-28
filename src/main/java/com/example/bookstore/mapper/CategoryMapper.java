package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.category.CategoryDto;
import com.example.bookstore.dto.category.CategoryResponseDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(config = MapperConfig.class)
@Component
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    CategoryResponseDto toResponseDto(Category category);

    Category toModel(CategoryDto categoryDto);

    Category toModel(CreateCategoryRequestDto categoryRequestDto);
}
