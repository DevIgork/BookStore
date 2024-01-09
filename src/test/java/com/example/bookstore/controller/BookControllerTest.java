package com.example.bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.bookstore.dto.book.BookDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    private static final Long CLEAN_CODER_ID = 1L;
    private static final Long ENGLISH_NOT_EASY_ID = 2L;
    private static final Long IT_ID = 3L;
    private static final Long EDUCATION_CATEGORY_ID = 1L;
    private static final Long HORROR_CATEGORY_ID = 2L;
    private static final BigDecimal ENGLISH_NOT_EASY_PRICE = new BigDecimal("461.00");
    private static final String ENGLISH_NOT_EASY_TITLE = "English is not easy";
    private static final String ENGLISH_NOT_EASY_AUTHOR = "Luci Gutierrez";
    private static final String ENGLISH_NOT_EASY_ISBN = "9789669820228";
    private static final String ENGLISH_NOT_EASY_DESCRIPTION = "If you"
            + " want to finally start speaking English, "
            + "but studying boring rules in textbooks drives you crazy, "
            + "then this book is for you!";
    private static final String ENGLISH_NOT_EASY_COVER_IMAGE = "https://book-ye.com.ua/upload/resize_cache"
            + "/iblock/21e/520_860_1/1f6d32c7_e05f_11e9_8121_000c29ae1566_73737"
            + "abd_593d_11ed_8175_0050568ef5e6.jpg";
    private static final BigDecimal CLEAN_CODER_PRICE =
            new BigDecimal("456.00");
    private static final String CLEAN_CODER_TITLE =
            "The Clean Coder: A Code of Conduct for Professional Programmers";
    private static final String CLEAN_CODER_AUTHOR =
            "Robert C. Martin";
    private static final String CLEAN_CODER_ISBN =
            "9789669820229";
    private static final String CLEAN_CODER_DESCRIPTION =
            "A Code of Conduct for Professional Programmers,"
            + " legendary software expert Robert C. Martin";
    private static final String CLEAN_CODER_COVER_IMAGE = "https://balka-book.com/files/2023/07_06/12_56/u_files_store_5_7.jpg";
    private static final String IT_TITLE = "It";
    private static final String IT_AUTHOR = "Stephen King";
    private static final String IT_ISBN = "9781444707861";
    private static final BigDecimal IT_PRICE =
            new BigDecimal("670.00");
    private static final String IT_DESCRIPTION =
            " In the storm drains, in the sewers, "
            + "IT lurked, taking on the shape of every nightmare, each one's deepest dread. "
            + "Sometimes IT reached up, seizing, tearing, killing ...The adults, knowing better,"
            + " knew nothing.";
    private static final String IT_COVER_IMAGE = "https://balka-book.com/files/2021"
            + "/05_03/21_39/u_files_store_1_503867.jpg";
    private static BookDto cleanCoderDto;
    private static BookDto englishNotEasyDto;
    private static BookDto itDto;
    private static List<BookDto> bookDtos;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        cleanCoderDto = new BookDto()
                .setIsbn(CLEAN_CODER_ISBN)
                .setTitle(CLEAN_CODER_TITLE)
                .setDescription(CLEAN_CODER_DESCRIPTION)
                .setPrice(CLEAN_CODER_PRICE)
                .setCoverImage(CLEAN_CODER_COVER_IMAGE)
                .setAuthor(CLEAN_CODER_AUTHOR)
                .setId(CLEAN_CODER_ID)
                .setCategoryIds(Set.of(EDUCATION_CATEGORY_ID));
        englishNotEasyDto = new BookDto()
                .setIsbn(ENGLISH_NOT_EASY_ISBN)
                .setTitle(ENGLISH_NOT_EASY_TITLE)
                .setDescription(ENGLISH_NOT_EASY_DESCRIPTION)
                .setPrice(ENGLISH_NOT_EASY_PRICE)
                .setCoverImage(ENGLISH_NOT_EASY_COVER_IMAGE)
                .setAuthor(ENGLISH_NOT_EASY_AUTHOR)
                .setId(ENGLISH_NOT_EASY_ID)
                .setCategoryIds(Set.of(EDUCATION_CATEGORY_ID));
        itDto = new BookDto()
                .setIsbn(IT_ISBN)
                .setTitle(IT_TITLE)
                .setDescription(IT_DESCRIPTION)
                .setPrice(IT_PRICE)
                .setCoverImage(IT_COVER_IMAGE)
                .setAuthor(IT_AUTHOR)
                .setId(IT_ID)
                .setCategoryIds(Set.of(HORROR_CATEGORY_ID));
        bookDtos = List.of(cleanCoderDto, englishNotEasyDto, itDto);
    }

    @WithMockUser(username = "User", roles = {"USER"})
    @Test
    @DisplayName("""
            get all books
            """)
    public void getAll_ValidData_Success() throws Exception {
        List<BookDto> expected = new ArrayList<>(bookDtos);

        //When
        MvcResult result = mockMvc.perform(
                get("/api/books")
        ).andReturn();

        //then
        List<BookDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<BookDto>>() {}
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
}
