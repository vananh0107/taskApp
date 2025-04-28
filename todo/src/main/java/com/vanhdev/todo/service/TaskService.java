package com.vanhdev.todo.service;

import com.vanhdev.todo.dto.request.TaskRequest;
import com.vanhdev.todo.dto.response.TaskResponse;
import com.vanhdev.todo.entity.Category;
import com.vanhdev.todo.entity.Task;
import com.vanhdev.todo.entity.User;
import com.vanhdev.todo.exception.AppException;
import com.vanhdev.todo.exception.ErrorCode;
import com.vanhdev.todo.mapper.TaskMapper;
import com.vanhdev.todo.repository.CategoryRepository;
import com.vanhdev.todo.repository.TaskRepository;
import com.vanhdev.todo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {
    TaskRepository taskRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    TaskMapper taskMapper;

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        Task task = taskMapper.toTask(request);
        task.setUser(user);
        task.setCategory(category);
        log.info(category.getName());
        log.info(user.getFirstName());
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    public Page<TaskResponse> getTasks(Pageable pageable) {
        Pageable defaultPageable = pageable.isPaged()
                ? pageable
                : Pageable.ofSize(10);
        return taskRepository.findAll(defaultPageable)
                .map(taskMapper::toTaskResponse);
    }


    public TaskResponse getTaskById(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_EXISTED));
        return taskMapper.toTaskResponse(task);
    }

    @Transactional
    public TaskResponse updateTask(String id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_EXISTED));

        Task updated = taskMapper.toTask(request);
        updated.setCreatedAt(task.getCreatedAt());
        updated.setId(id);
        updated.setUser(task.getUser());
        updated.setUpdatedAt(LocalDateTime.now());

        return taskMapper.toTaskResponse(taskRepository.save(updated));
    }

    @Transactional
    public void deleteTask(String id) {
        if (!taskRepository.existsById(id)) {
            throw new AppException(ErrorCode.TASK_NOT_EXISTED);
        }
        taskRepository.deleteById(id);
    }

}
