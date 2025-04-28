package com.vanhdev.todo.service;

import com.vanhdev.todo.dto.request.TaskRequest;
import com.vanhdev.todo.dto.response.TaskResponse;
import com.vanhdev.todo.entity.Category;
import com.vanhdev.todo.entity.Task;
import com.vanhdev.todo.entity.User;
import com.vanhdev.todo.exception.AppException;
import com.vanhdev.todo.mapper.TaskMapper;
import com.vanhdev.todo.repository.CategoryRepository;
import com.vanhdev.todo.repository.TaskRepository;
import com.vanhdev.todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskService taskService;
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        userRepository = mock(UserRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        taskMapper = mock(TaskMapper.class);
        taskService = new TaskService(taskRepository, userRepository, categoryRepository, taskMapper);
    }

    @Test
    void createTask_success() {
        TaskRequest request = new TaskRequest();
        request.setUserId("userId");
        request.setCategoryId("categoryId");
        request.setTitle("Test Task");
        request.setDescription("Test Task Description");

        User user = new User();
        Category category = new Category();
        Task task = new Task();

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.of(category));
        when(taskMapper.toTask(request)).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toTaskResponse(task)).thenReturn(new TaskResponse());

        TaskResponse response = taskService.createTask(request);

        assertNotNull(response);
        verify(taskRepository).save(any());
    }

    @Test
    void createTask_userNotFound() {
        TaskRequest request = new TaskRequest();
        request.setUserId("invalidUserId");

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> taskService.createTask(request));
    }

    @Test
    void updateTask_success() {
        String taskId = "123";
        TaskRequest request = new TaskRequest();
        request.setUserId("userId");
        request.setCategoryId("categoryId");
        request.setTitle("Updated Task");
        request.setDescription("Updated Task Description");

        Task existingTask = new Task();
        existingTask.setId(taskId);
        User user = new User();
        Category category = new Category();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.of(category));
        when(taskMapper.toTask(request)).thenReturn(new Task());
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);
        when(taskMapper.toTaskResponse(existingTask)).thenReturn(new TaskResponse());

        TaskResponse response = taskService.updateTask(taskId, request);

        assertNotNull(response);
        verify(taskRepository).save(any());
    }

    @Test
    void updateTask_taskNotFound() {
        String taskId = "invalidTaskId";
        TaskRequest request = new TaskRequest();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> taskService.updateTask(taskId, request));
    }

    @Test
    void getTaskById_success() {
        String taskId = "123";
        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task)).thenReturn(new TaskResponse());

        TaskResponse response = taskService.getTaskById(taskId);

        assertNotNull(response);
    }

    @Test
    void getTaskById_taskNotFound() {
        String taskId = "invalidTaskId";

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void deleteTask_success() {
        String taskId = "123";

        when(taskRepository.existsById(taskId)).thenReturn(true);

        taskService.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void deleteTask_taskNotFound() {
        String taskId = "invalidTaskId";

        when(taskRepository.existsById(taskId)).thenReturn(false);

        assertThrows(AppException.class, () -> taskService.deleteTask(taskId));
    }

    @Test
    void getTasks_withPagedPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        Task task = new Task();
        when(taskRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(task)));
        when(taskMapper.toTaskResponse(any(Task.class))).thenReturn(new TaskResponse());

        var result = taskService.getTasks(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(taskRepository).findAll(pageable);
    }

    @Test
    void getTasks_withUnpagedPageable() {
        Pageable pageable = Pageable.unpaged();
        Pageable defaultPageable = Pageable.ofSize(10);

        Task task = new Task();
        when(taskRepository.findAll(defaultPageable)).thenReturn(new PageImpl<>(List.of(task)));
        when(taskMapper.toTaskResponse(any(Task.class))).thenReturn(new TaskResponse());

        var result = taskService.getTasks(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(taskRepository).findAll(defaultPageable);
    }
}
