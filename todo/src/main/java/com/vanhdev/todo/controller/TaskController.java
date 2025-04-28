package com.vanhdev.todo.controller;

import com.vanhdev.todo.dto.request.ApiResponse;
import com.vanhdev.todo.dto.request.TaskRequest;
import com.vanhdev.todo.dto.response.TaskResponse;
import com.vanhdev.todo.service.TaskService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;

    @PostMapping
    public ApiResponse<TaskResponse> createTask(@RequestBody @Valid TaskRequest request) {
        return ApiResponse.<TaskResponse>builder()
                .result(taskService.createTask(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<TaskResponse>> getTasks(
            Pageable pageable
    ) {
        return ApiResponse.<Page<TaskResponse>>builder()
                .result(taskService.getTasks(pageable))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TaskResponse> getTaskById(@PathVariable String id) {
        return ApiResponse.<TaskResponse>builder()
                .result(taskService.getTaskById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TaskResponse> updateTask(
            @PathVariable String id,
            @RequestBody @Valid TaskRequest request
    ) {
        return ApiResponse.<TaskResponse>builder()
                .result(taskService.updateTask(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ApiResponse.<Void>builder()
                .result(null)
                .build();
    }

}