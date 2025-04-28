package com.vanhdev.todo.controller;

import com.vanhdev.todo.dto.request.ApiResponse;
import com.vanhdev.todo.dto.request.TaskRequest;
import com.vanhdev.todo.dto.response.TaskResponse;
import com.vanhdev.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskRequest taskRequest;
    private TaskResponse taskResponse;
    private String taskId;

    @BeforeEach
    void setUp() {
        taskId = "task-123";
        taskRequest = new TaskRequest();

        taskResponse = new TaskResponse();
    }

    @Test
    void createTask_shouldCallServiceAndReturnResponse() {
        when(taskService.createTask(any(TaskRequest.class))).thenReturn(taskResponse);

        ApiResponse<TaskResponse> apiResponse = taskController.createTask(taskRequest);

        assertNotNull(apiResponse);
        assertEquals(taskResponse, apiResponse.getResult());
        verify(taskService, times(1)).createTask(any(TaskRequest.class));
    }

    @Test
    void getTasks_shouldCallServiceAndReturnPageResponse() {
        Pageable pageable = PageRequest.of(0, 10);
        List<TaskResponse> taskList = Collections.singletonList(taskResponse);
        Page<TaskResponse> taskPage = new PageImpl<>(taskList, pageable, 1);
        when(taskService.getTasks(any(Pageable.class))).thenReturn(taskPage);

        ApiResponse<Page<TaskResponse>> apiResponse = taskController.getTasks(pageable);

        assertNotNull(apiResponse);
        assertEquals(taskPage, apiResponse.getResult());
        assertNotNull(apiResponse.getResult());
        assertEquals(1, apiResponse.getResult().getTotalElements());
        assertEquals(taskResponse, apiResponse.getResult().getContent().get(0));
        verify(taskService, times(1)).getTasks(any(Pageable.class));
    }

    @Test
    void getTaskById_shouldCallServiceAndReturnResponse() {
        when(taskService.getTaskById(eq(taskId))).thenReturn(taskResponse);

        ApiResponse<TaskResponse> apiResponse = taskController.getTaskById(taskId);

        assertNotNull(apiResponse);
        assertEquals(taskResponse, apiResponse.getResult());
        verify(taskService, times(1)).getTaskById(eq(taskId));
    }

    @Test
    void updateTask_shouldCallServiceAndReturnResponse() {
        TaskResponse updatedTaskResponse = new TaskResponse();

        when(taskService.updateTask(eq(taskId), any(TaskRequest.class))).thenReturn(updatedTaskResponse);

        ApiResponse<TaskResponse> apiResponse = taskController.updateTask(taskId, taskRequest);

        assertNotNull(apiResponse);
        assertEquals(updatedTaskResponse, apiResponse.getResult());
        verify(taskService, times(1)).updateTask(eq(taskId), any(TaskRequest.class));
    }

    @Test
    void deleteTask_shouldCallServiceAndReturnVoidResponse() {

        ApiResponse<Void> apiResponse = taskController.deleteTask(taskId);

        assertNotNull(apiResponse);
        assertNull(apiResponse.getResult());

        verify(taskService, times(1)).deleteTask(eq(taskId));
    }
}
