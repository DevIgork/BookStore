package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static final Long CLEAN_CODER_ID = 1L;
    private static final Long ENGLISH_NOT_EASY_ID = 2L;
    private static final Long EDUCATION_CATEGORY_ID = 1L;
    private static Book englishNotEasy;
    private static Book cleanCoder;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void initialBooks(){
        cleanCoder = new Book();
        englishNotEasy = new Book();
        cleanCoder.setId(CLEAN_CODER_ID);
        cleanCoder.setPrice(BigDecimal.valueOf(456.00));
        cleanCoder.setTitle("The Clean Coder: A Code of Conduct for Professional Programmers");
        cleanCoder.setAuthor("Robert C. Martin");
        cleanCoder.setIsbn("9789669820229");
        cleanCoder.setDescription("A Code of Conduct for Professional Programmers,"
                + " legendary software expert Robert C. Martin");
        cleanCoder.setCoverImage("https://balka-book.com/files/2023/07_06/12_56/u_files_store_5_7.jpg");
        englishNotEasy.setId(ENGLISH_NOT_EASY_ID);
        englishNotEasy.setPrice(BigDecimal.valueOf(461.00));
        englishNotEasy.setTitle("English is not easy");
        englishNotEasy.setAuthor("Luci Gutiérrez");
        englishNotEasy.setIsbn("9789669820228");
        englishNotEasy.setDescription("If you want to finally start speaking English, "
                + "but studying boring rules in textbooks drives you crazy, "
                + "then this book is for you!");
        englishNotEasy.setCoverImage("https://book-ye.com.ua/upload/resize_cache"
                + "/iblock/21e/520_860_1/1f6d32c7_e05f_11e9_8121_000c29ae1566_73737"
                + "abd_593d_11ed_8175_0050568ef5e6.jpg");
    }

    @Test
    @DisplayName("""
                    get books by categories id
                    """)
    void getBookByCategoriesId_TwoCategories_ReturnListOneBook(){
        List<Book> actual = bookRepository.findBookByCategoriesId(EDUCATION_CATEGORY_ID);
        List<Book> expected = List.of(cleanCoder, englishNotEasy);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(expected, actual);
    }

}
