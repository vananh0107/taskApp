package com.vanhdev.todo.service;

import com.vanhdev.todo.dto.request.CategoryRequest;
import com.vanhdev.todo.dto.response.CategoryResponse;
import com.vanhdev.todo.entity.Category;
import com.vanhdev.todo.exception.AppException;
import com.vanhdev.todo.exception.ErrorCode;
import com.vanhdev.todo.mapper.CategoryMapper;
import com.vanhdev.todo.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    private void checkCategoryNameExists(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        checkCategoryNameExists(request.getName());

        Category category = categoryMapper.toCategory(request);
        Category saved = categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(saved);
    }

    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse updateCategory(String id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        if (!category.getName().equals(request.getName())) {
            checkCategoryNameExists(request.getName());
        }

        category.setName(request.getName());

        Category updated = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(updated);
    }

    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }

        categoryRepository.deleteById(id);
    }
}
