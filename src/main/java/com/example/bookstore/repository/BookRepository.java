package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT b from Book b join fetch b.categories c where c.id=:id")
    List<Book> findBookByCategoriesId(Long id);

    @Query("SELECT b from Book b join fetch b.categories where b.id=:id")
    Optional<Book> findById(Long id);
}
