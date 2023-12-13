package com.example.bookstore.repository.impl;

import com.example.bookstore.dto.BookSearchParameters;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.SpecificationBuilder;
import com.example.bookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        spec = searchTitle(searchParameters, spec);
        spec = searchAuthor(searchParameters, spec);
        return spec;
    }

    private Specification<Book> searchTitle(
            BookSearchParameters searchParameters,
            Specification<Book> spec
    ) {
        if (searchParameters.title() != null
                && searchParameters.title().length > 0) {
            spec = spec.and(
                    bookSpecificationProviderManager.getSpecificationProvider(KEY_TITLE)
                            .getSpecification(searchParameters.title())
            );
        }
        return spec;
    }

    private Specification<Book> searchAuthor(
            BookSearchParameters searchParameters,
            Specification<Book> spec
    ) {
        if (searchParameters.author() != null
                && searchParameters.author().length > 0) {
            spec = spec.and(
                    bookSpecificationProviderManager.getSpecificationProvider(KEY_AUTHOR)
                            .getSpecification(searchParameters.author())
            );
        }
        return spec;
    }
}
