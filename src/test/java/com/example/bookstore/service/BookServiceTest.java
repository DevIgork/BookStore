package com.example.bookstore.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.impl.BookSeviceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
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
    private static final String CLEAN_CODER_AUTHOR = "Robert C. Martin";
    private static final String CLEAN_CODER_ISBN =
            "9789669820229";
    private static final String CLEAN_CODER_DESCRIPTION =
            "A Code of Conduct for Professional Programmers,"
            + " legendary software expert Robert C. Martin";
    private static final String CLEAN_CODER_COVER_IMAGE = "https://balka-book.com/files/2023/07_06/12_56/u_files_store_5_7.jpg";

    private static Book englishNotEasy;
    private static Book cleanCoder;
    private static BookDto englishNotEasyDto;
    private static BookDto cleanCoderDto;
    private static List<Book> books;
    private static List<BookDto> bookDtos;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookSeviceImpl bookService;

    @BeforeAll
    public static void beforeAll() {
        englishNotEasyDto = new BookDto()
                .setId(ENGLISH_NOT_EASY_ID)
                .setPrice(ENGLISH_NOT_EASY_PRICE)
                .setTitle(ENGLISH_NOT_EASY_TITLE)
                .setAuthor(ENGLISH_NOT_EASY_AUTHOR)
                .setIsbn(ENGLISH_NOT_EASY_ISBN)
                .setDescription(ENGLISH_NOT_EASY_DESCRIPTION)
                .setCoverImage(ENGLISH_NOT_EASY_COVER_IMAGE);
        cleanCoderDto = new BookDto()
                .setId(CLEAN_CODER_ID)
                .setPrice(CLEAN_CODER_PRICE)
                .setTitle(CLEAN_CODER_TITLE)
                .setAuthor(CLEAN_CODER_AUTHOR)
                .setIsbn(CLEAN_CODER_ISBN)
                .setDescription(CLEAN_CODER_DESCRIPTION)
                .setCoverImage(CLEAN_CODER_COVER_IMAGE);
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
        books = List.of(cleanCoder, englishNotEasy);
        bookDtos = List.of(cleanCoderDto, englishNotEasyDto);
    }

    @Test
    @DisplayName("""
            Save a new book and return valid DTO
            """)
    public void createBook_validCreateBookRequestDto_ReturnBookDto() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto()
                .setAuthor(CLEAN_CODER_AUTHOR)
                .setIsbn(CLEAN_CODER_ISBN)
                .setTitle(CLEAN_CODER_TITLE)
                .setPrice(CLEAN_CODER_PRICE)
                .setCoverImage(CLEAN_CODER_COVER_IMAGE)
                .setDescription(CLEAN_CODER_DESCRIPTION);

        when(bookMapper.toModel(createBookRequestDto)).thenReturn(cleanCoder);
        when(bookRepository.save(cleanCoder)).thenReturn(cleanCoder);
        when(bookMapper.toDto(cleanCoder)).thenReturn(cleanCoderDto);

        BookDto savedBookDto = bookService.createBook(createBookRequestDto);
        assertThat(savedBookDto).isEqualTo(cleanCoderDto);
        verify(bookRepository, times(1)).save(cleanCoder);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("""
            Get all books return list of valid bookDto
            """)
    public void get_validData_returnListOfBookDto() {
        Pageable pageable = Pageable.ofSize(2);
        PageImpl<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(books.get(0))).thenReturn(cleanCoderDto);
        when(bookMapper.toDto(books.get(1))).thenReturn(englishNotEasyDto);

        List<BookDto> all = bookService.getAll(pageable);
        assertThat(all).isEqualTo(bookDtos);
        verify(bookRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("""
            Get valid bookDto by id
            """)
    public void getBookById_ValidData_ReturnBookDto() {
        Optional<Book> clearCoderOptional = Optional.of(cleanCoder);

        when(bookRepository.findById(CLEAN_CODER_ID)).thenReturn(clearCoderOptional);
        when(bookMapper.toDto(cleanCoder)).thenReturn(cleanCoderDto);

        BookDto bookByIdDto = bookService.getBookById(CLEAN_CODER_ID);
        assertThat(bookByIdDto).isEqualTo(cleanCoderDto);
        verify(bookRepository, times(1)).findById(CLEAN_CODER_ID);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }
}
