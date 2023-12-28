package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT b from Book b join fetch b.categories c where c.id=:id")
    List<Book> getBookByCategoriesId(Long id);

    @Query("SELECT b from Book b join fetch b.categories where b.id=:id")
    Optional<Book> findById(Long id);

    @Query("SELECT b FROM Book b JOIN FETCH b.categories")
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"only_id"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll(Specification<Book> bookSpecification);
}
