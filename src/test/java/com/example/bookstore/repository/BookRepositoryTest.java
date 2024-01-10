package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static final Long CLEAN_CODER_ID = 1L;
    private static final Long ENGLISH_NOT_EASY_ID = 2L;
    private static final BigDecimal ENGLISH_NOT_EASY_PRICE = new BigDecimal("461.00");
    private static final String ENGLISH_NOT_EASY_TITLE = "English is not easy";
    private static final String ENGLISH_NOT_EASY_AUTHOR = "Luci Gutierrez";
    private static final String ENGLISH_NOT_EASY_ISBN = "9789669820228";
    private static final String ENGLISH_NOT_EASY_DESCRIPTION =
            "If you want to finally start speaking English, "
            + "but studying boring rules in textbooks drives you crazy, "
            + "then this book is for you!";
    private static final String ENGLISH_NOT_EASY_COVER_IMAGE = "https://book-ye.com.ua/upload/resize_cache"
            + "/iblock/21e/520_860_1/1f6d32c7_e05f_11e9_8121_000c29ae1566_73737"
            + "abd_593d_11ed_8175_0050568ef5e6.jpg";
    private static final BigDecimal CLEAN_CODER_PRICE = new BigDecimal("456.00");
    private static final String CLEAN_CODER_TITLE =
            "The Clean Coder: A Code of Conduct for Professional Programmers";
    private static final String CLEAN_CODER_AUTHOR =
            "Robert C. Martin";
    private static final String CLEAN_CODER_ISBN = "9789669820229";
    private static final String CLEAN_CODER_DESCRIPTION =
            "A Code of Conduct for Professional Programmers,"
            + " legendary software expert Robert C. Martin";
    private static final String CLEAN_CODER_COVER_IMAGE = "https://balka-book.com/files/2023/07_06/12_56/u_files_store_5_7.jpg";
    private static final Long EDUCATION_CATEGORY_ID = 1L;
    private static final Long NON_EXISTING_ID = 5L;
    private static Book englishNotEasy;
    private static Book cleanCoder;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void initialBooks() {
        cleanCoder = new Book();
        cleanCoder.setId(CLEAN_CODER_ID);
        cleanCoder.setPrice(CLEAN_CODER_PRICE);
        cleanCoder.setTitle(CLEAN_CODER_TITLE);
        cleanCoder.setAuthor(CLEAN_CODER_AUTHOR);
        cleanCoder.setIsbn(CLEAN_CODER_ISBN);
        cleanCoder.setDescription(CLEAN_CODER_DESCRIPTION);
        cleanCoder.setCoverImage(CLEAN_CODER_COVER_IMAGE);
        englishNotEasy = new Book();
        englishNotEasy.setId(ENGLISH_NOT_EASY_ID);
        englishNotEasy.setPrice(ENGLISH_NOT_EASY_PRICE);
        englishNotEasy.setTitle(ENGLISH_NOT_EASY_TITLE);
        englishNotEasy.setAuthor(ENGLISH_NOT_EASY_AUTHOR);
        englishNotEasy.setIsbn(ENGLISH_NOT_EASY_ISBN);
        englishNotEasy.setDescription(ENGLISH_NOT_EASY_DESCRIPTION);
        englishNotEasy.setCoverImage(ENGLISH_NOT_EASY_COVER_IMAGE);
    }

    @Test
    @DisplayName("""
                    get books by categories id
                    """)
    void getBookByCategoriesId_TwoCategories_ReturnListOneBook() {
        List<Book> expected = List.of(cleanCoder, englishNotEasy);
        List<Book> actual = bookRepository.findBookByCategoriesId(EDUCATION_CATEGORY_ID);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            get book by id
            """)
    void getBookById_ThreeBooks_ReturnOptionalBook() {
        Book expected = cleanCoder;
        Book actual = bookRepository.findById(CLEAN_CODER_ID).get();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            get a non-existent book
            """)
    void getBookById_ThreeBooks_ReturnNull() {
        Optional<Book> expected = Optional.ofNullable(null);
        Optional<Book> actual = bookRepository.findById(NON_EXISTING_ID);
        Assertions.assertEquals(expected, actual);

    }
}
