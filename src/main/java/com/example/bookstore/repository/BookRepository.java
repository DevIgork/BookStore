package com.example.bookstore.repository;

import com.example.bookstore.dto.CreateBookRequestDto;
import com.example.bookstore.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Book b "
            + "SET b.title = :#{#createBookRequestDto.title},"
            + " b.author = :#{#createBookRequestDto.author},"
            + " b.isbn = :#{#createBookRequestDto.isbn},"
            + " b.price = :#{#createBookRequestDto.price},"
            + " b.description = :#{#createBookRequestDto.description},"
            + " b.coverImage = :#{#createBookRequestDto.coverImage}"
            + " WHERE b.id = :id")
    void updateBookById(Long id, CreateBookRequestDto createBookRequestDto);
}
