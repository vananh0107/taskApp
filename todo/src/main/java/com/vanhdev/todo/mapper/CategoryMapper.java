package com.vanhdev.todo.mapper;

import com.vanhdev.todo.dto.request.CategoryRequest;
import com.vanhdev.todo.dto.response.CategoryResponse;
import com.vanhdev.todo.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Category toCategory(CategoryRequest request);
}
