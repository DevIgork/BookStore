package com.example.bookstore.service;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto createBook(CreateBookRequestDto createBookRequestDto);

    List<BookDto> getAll();

    BookDto getBookById(Long id);

    void deleteById(Long id);

    void updateById(Long id, CreateBookRequestDto createBookRequestDto);
}
