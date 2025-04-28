package com.vanhdev.todo.mapper;

import com.vanhdev.todo.dto.request.TaskRequest;
import com.vanhdev.todo.dto.response.TaskResponse;
import com.vanhdev.todo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class})
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task toTask(TaskRequest request);

    TaskResponse toTaskResponse(Task task);
}

