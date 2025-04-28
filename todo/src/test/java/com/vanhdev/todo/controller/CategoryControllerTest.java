package com.vanhdev.todo.controller;

import com.vanhdev.todo.dto.request.ApiResponse;
import com.vanhdev.todo.dto.request.CategoryRequest;
import com.vanhdev.todo.dto.response.CategoryResponse;
import com.vanhdev.todo.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;
    private String categoryId;

    @BeforeEach
    void setUp() {
        categoryId = "cat-789fgh";

        categoryRequest = new CategoryRequest();
        categoryResponse = new CategoryResponse();
    }

    @Test
    void createCategory_shouldCallServiceAndReturnResponse() {
        when(categoryService.createCategory(any(CategoryRequest.class))).thenReturn(categoryResponse);

        ApiResponse<CategoryResponse> apiResponse = categoryController.createCategory(categoryRequest);

        assertNotNull(apiResponse, "The API response should not be null.");
        assertEquals(categoryResponse, apiResponse.getResult(), "The result in the API response should match the expected category response.");

        verify(categoryService, times(1)).createCategory(any(CategoryRequest.class));
    }

    @Test
    void getAllCategories_shouldCallServiceAndReturnListResponse() {
        List<CategoryResponse> categoryList = Collections.singletonList(categoryResponse);
        when(categoryService.getAllCategories()).thenReturn(categoryList);

        ApiResponse<List<CategoryResponse>> apiResponse = categoryController.getAllCategories();

        assertNotNull(apiResponse, "The API response should not be null.");
        assertNotNull(apiResponse.getResult(), "The result list in the API response should not be null.");
        assertEquals(1, apiResponse.getResult().size(), "The result list should contain one category.");
        assertEquals(categoryResponse, apiResponse.getResult().get(0), "The category in the list should match the expected response.");

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getCategoryById_shouldCallServiceAndReturnResponse() {
        when(categoryService.getCategoryById(eq(categoryId))).thenReturn(categoryResponse);

        ApiResponse<CategoryResponse> apiResponse = categoryController.getCategoryById(categoryId);

        assertNotNull(apiResponse, "The API response should not be null.");
        assertEquals(categoryResponse, apiResponse.getResult(), "The result should match the expected category response.");

        verify(categoryService, times(1)).getCategoryById(eq(categoryId));
    }

    @Test
    void updateCategory_shouldCallServiceAndReturnResponse() {
        CategoryResponse updatedCategoryResponse = new CategoryResponse();

        when(categoryService.updateCategory(eq(categoryId), any(CategoryRequest.class))).thenReturn(updatedCategoryResponse);

        ApiResponse<CategoryResponse> apiResponse = categoryController.updateCategory(categoryId, categoryRequest);

        assertNotNull(apiResponse, "The API response should not be null.");
        assertEquals(updatedCategoryResponse, apiResponse.getResult(), "The result should match the updated category response.");

        verify(categoryService, times(1)).updateCategory(eq(categoryId), any(CategoryRequest.class));
    }

    @Test
    void deleteCategory_shouldCallServiceAndReturnVoidResponse() {
        ApiResponse<Void> apiResponse = categoryController.deleteCategory(categoryId);

        assertNotNull(apiResponse, "The API response should not be null.");
        assertNull(apiResponse.getResult(), "The result in the API response should be null for a void operation.");

        verify(categoryService, times(1)).deleteCategory(eq(categoryId));
    }
}
