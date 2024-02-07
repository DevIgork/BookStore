package com.example.bookstore.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.category.CategoryDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.mapper.CategoryMapper;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.impl.CategoryServiceImpl;
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
public class CategoryServiceTest {
    private static final Long EDUCATION_ID = 1L;
    private static final Long HORROR_ID = 2L;
    private static final String EDUCATION_NAME = "education";
    private static final String HORROR_NAME = "horror";
    private static final String EDUCATION_DESCRIPTION = "book for learning";
    private static final String HORROR_DESCRIPTION =
            "a story in which the focus is on creating a feeling of fear";
    private static Category education;
    private static Category horror;
    private static CategoryDto educationDto;
    private static CategoryDto horrorDto;
    private static List<Category> categories;
    private static List<CategoryDto> categoryDtos;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @BeforeAll
    public static void beforeAll() {
        education = new Category();
        education.setId(EDUCATION_ID);
        education.setName(EDUCATION_NAME);
        education.setDescription(EDUCATION_DESCRIPTION);
        horror = new Category();
        horror.setDescription(HORROR_DESCRIPTION);
        horror.setName(HORROR_NAME);
        horror.setId(HORROR_ID);
        educationDto = new CategoryDto()
                .setId(EDUCATION_ID)
                .setDescription(EDUCATION_DESCRIPTION)
                .setName(EDUCATION_NAME);
        horrorDto = new CategoryDto()
                .setId(HORROR_ID)
                .setName(HORROR_NAME)
                .setDescription(HORROR_DESCRIPTION);
        categories = List.of(education, horror);
        categoryDtos = List.of(educationDto, horrorDto);
    }

    @Test
    @DisplayName("""
            Get all categories return list of valid categoryDto
            """)
    public void getAll_validData_ReturnListOfCategory() {
        Pageable pageable = Pageable.ofSize(2);
        PageImpl<Category> categoryPage = new PageImpl<>(categories);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(education)).thenReturn(educationDto);
        when(categoryMapper.toDto(horror)).thenReturn(horrorDto);

        List<CategoryDto> allCategory = categoryService.getAll(pageable);
        assertThat(allCategory).isEqualTo(categoryDtos);
        verify(categoryRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("""
            Get valid category by Id
            """)
    public void getCategoryById_ValidData_ReturnCategoryDto() {
        Optional<Category> educationOptional = Optional.of(education);

        when(categoryRepository.findById(EDUCATION_ID)).thenReturn(educationOptional);
        when(categoryMapper.toDto(education)).thenReturn(educationDto);

        CategoryDto categoryById = categoryService.getById(EDUCATION_ID);
        assertThat(categoryById).isEqualTo(educationDto);
        verify(categoryRepository, times(1)).findById(EDUCATION_ID);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("""
            Save a new category and return valid DTO
            """)
    public void createCategory_validData_ReturnCategoryId() {
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto()
                .setDescription(HORROR_DESCRIPTION)
                .setName(HORROR_NAME);

        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(horror);
        when(categoryRepository.save(horror)).thenReturn(horror);
        when(categoryMapper.toDto(horror)).thenReturn(horrorDto);

        CategoryDto savedCategory = categoryService.createCategory(categoryRequestDto);
        assertThat(savedCategory).isEqualTo(horrorDto);
        verify(categoryRepository, times(1)).save(horror);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }
}
