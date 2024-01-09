package com.example.bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.bookstore.dto.category.CategoryDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private static final Long EDUCATION_ID = 1L;
    private static final Long HORROR_ID = 2L;
    private static final Long BAD_CODE_ID = 3L;
    private static final String BAD_CODE_NAME = "badCode";
    private static final String EDUCATION_NAME = "education";
    private static final String HORROR_NAME = "horror";
    private static final String BAD_CODE_DESCRIPRION = "LEARN HOW TO WRITE BAD CODE";
    private static final String EDUCATION_DESCRIPTION = "book for learning";
    private static final String HORROR_DESCRIPTION =
            "a story in which the focus is on creating a feeling of fear";
    private static CategoryDto educationDto;
    private static CategoryDto horrorDto;
    private static List<CategoryDto> categoryDtos;

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
        educationDto = new CategoryDto()
                .setId(EDUCATION_ID)
                .setDescription(EDUCATION_DESCRIPTION)
                .setName(EDUCATION_NAME);
        horrorDto = new CategoryDto()
                .setId(HORROR_ID)
                .setDescription(HORROR_DESCRIPTION)
                .setName(HORROR_NAME);
        categoryDtos = List.of(educationDto, horrorDto);
    }

    @WithMockUser(username = "User", roles = {"USER"})
    @Test
    @DisplayName("""
            get all category
            """)
    public void getAll_ValidData_Success() throws Exception {
        //given
        List<CategoryDto> expected = new ArrayList<>(categoryDtos);

        //When
        MvcResult result = mockMvc.perform(
                get("/api/categories")
        ).andReturn();

        //then
        List<CategoryDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<CategoryDto>>() {}
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @WithMockUser(username = "Admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "classpath:database/category/remove_bad_code.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            create category
            """)
    public void createBook_ValidData_Success() throws Exception {
        // Given
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto()
                .setName(BAD_CODE_NAME)
                .setDescription(BAD_CODE_DESCRIPRION);

        CategoryDto expected = new CategoryDto()
                .setId(BAD_CODE_ID)
                .setName(BAD_CODE_NAME)
                .setDescription(BAD_CODE_DESCRIPRION);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        //When
        MvcResult result = mockMvc.perform(
                post("/api/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //Then
        CategoryDto actual = objectMapper
                .readValue(
                        result
                        .getResponse()
                        .getContentAsString(),
                        CategoryDto.class
                );
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
