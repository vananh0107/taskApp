package com.vanhdev.todo.service;

import com.vanhdev.todo.dto.request.CategoryRequest;
import com.vanhdev.todo.dto.response.CategoryResponse;
import com.vanhdev.todo.entity.Category;
import com.vanhdev.todo.exception.AppException;
import com.vanhdev.todo.mapper.CategoryMapper;
import com.vanhdev.todo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryMapper = mock(CategoryMapper.class);
        categoryService = new CategoryService(categoryRepository, categoryMapper);
    }

    @Test
    void createCategory_success() {
        CategoryRequest request = new CategoryRequest();
        request.setName("New Category");

        Category category = new Category();
        category.setName(request.getName());

        when(categoryRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryMapper.toCategory(request)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toCategoryResponse(category)).thenReturn(new CategoryResponse());

        CategoryResponse response = categoryService.createCategory(request);

        assertNotNull(response);
        verify(categoryRepository).save(any());
    }

    @Test
    void createCategory_categoryExists() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Existing Category");

        when(categoryRepository.existsByName(request.getName())).thenReturn(true);

        assertThrows(AppException.class, () -> categoryService.createCategory(request));
    }

    @Test
    void getCategoryById_success() {
        String categoryId = "123";
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toCategoryResponse(category)).thenReturn(new CategoryResponse());

        CategoryResponse response = categoryService.getCategoryById(categoryId);

        assertNotNull(response);
    }

    @Test
    void getCategoryById_categoryNotFound() {
        String categoryId = "invalidId";

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @Test
    void getAllCategories_success() {
        Category category1 = new Category();
        category1.setName("Category 1");
        Category category2 = new Category();
        category2.setName("Category 2");

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));
        when(categoryMapper.toCategoryResponse(category1)).thenReturn(new CategoryResponse());
        when(categoryMapper.toCategoryResponse(category2)).thenReturn(new CategoryResponse());

        List<CategoryResponse> response = categoryService.getAllCategories();

        assertEquals(2, response.size());
    }

    @Test
    void updateCategory_success() {
        String categoryId = "123";
        CategoryRequest request = new CategoryRequest();
        request.setName("Updated Category");

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Old Category");

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName(request.getName());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(updatedCategory.getId());
        categoryResponse.setName(updatedCategory.getName());

        when(categoryMapper.toCategoryResponse(updatedCategory)).thenReturn(categoryResponse);

        CategoryResponse response = categoryService.updateCategory(categoryId, request);

        assertNotNull(response);
        assertEquals("Updated Category", response.getName());
    }



    @Test
    void updateCategory_categoryNotFound() {
        String categoryId = "invalidId";
        CategoryRequest request = new CategoryRequest();
        request.setName("Updated Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> categoryService.updateCategory(categoryId, request));
    }

    @Test
    void deleteCategory_success() {
        String categoryId = "123";

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    void deleteCategory_categoryNotFound() {
        String categoryId = "invalidId";

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        assertThrows(AppException.class, () -> categoryService.deleteCategory(categoryId));
    }
}
