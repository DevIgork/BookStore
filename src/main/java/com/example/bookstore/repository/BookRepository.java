package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query(
            value = "SELECT * FROM books LEFT JOIN books_categories ON books.id = books_categories.book_id WHERE books_categories.category_id = ?1",
            nativeQuery = true
    )
    List<Book> getBookByCategoriesId(Long id);
}
