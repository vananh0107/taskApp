package com.vanhdev.todo.controller;
import com.vanhdev.todo.dto.request.ApiResponse;
import com.vanhdev.todo.dto.request.UserCreationRequest;
import com.vanhdev.todo.dto.response.UserResponse;
import com.vanhdev.todo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userCreationRequest = new UserCreationRequest();

        userResponse = new UserResponse();
    }

    @Test
    void createUser_shouldCallUserServiceAndReturnApiResponse() {
        when(userService.createUser(any(UserCreationRequest.class))).thenReturn(userResponse);

        ApiResponse<UserResponse> apiResponse = userController.createUser(userCreationRequest);

        assertNotNull(apiResponse);
        assertNotNull(apiResponse.getResult());
        assertEquals(userResponse, apiResponse.getResult());

        verify(userService, times(1)).createUser(any(UserCreationRequest.class));
    }

    @Test
    void getMyInfo_shouldCallUserServiceAndReturnApiResponse() {
        when(userService.getMyInfo()).thenReturn(userResponse);

        ApiResponse<UserResponse> apiResponse = userController.getMyInfo();

        assertNotNull(apiResponse);
        assertNotNull(apiResponse.getResult());
        assertEquals(userResponse, apiResponse.getResult());

        verify(userService, times(1)).getMyInfo();
    }
}
