package com.vanhdev.todo.service;

import com.vanhdev.todo.dto.request.UserCreationRequest;
import com.vanhdev.todo.dto.request.UserUpdateRequest;
import com.vanhdev.todo.dto.response.UserResponse;
import com.vanhdev.todo.entity.Role;
import com.vanhdev.todo.entity.User;
import com.vanhdev.todo.exception.AppException;
import com.vanhdev.todo.exception.ErrorCode;
import com.vanhdev.todo.mapper.UserMapper;
import com.vanhdev.todo.repository.RoleRepository;
import com.vanhdev.todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_success() {
        UserCreationRequest request = new UserCreationRequest(); // tạo request
        request.setPassword("password");

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        Mockito.when(userMapper.toUser(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findById(any())).thenReturn(Optional.of(new Role()));
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toUserResponse(any())).thenReturn(new UserResponse());

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        verify(userRepository).save(any());
    }

    @Test
    void createUser_userAlreadyExists_throwsException() {
        UserCreationRequest request = new UserCreationRequest();
        request.setPassword("password");

        User user = new User();

        when(userMapper.toUser(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findById(any())).thenReturn(Optional.of(new Role()));
        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        AppException exception = assertThrows(AppException.class, () -> userService.createUser(request));
        assertEquals(ErrorCode.USER_EXISTED, exception.getErrorCode());
    }

    @Test
    void getMyInfo_success() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(new UserResponse());

        UserResponse response = userService.getMyInfo();

        assertNotNull(response);
        verify(userRepository).findByUsername("testUser");
    }

    @Test
    void getMyInfo_userNotFound_throwsException() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("notExistUser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        when(userRepository.findByUsername("notExistUser")).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> userService.getMyInfo());
        assertEquals(ErrorCode.USER_NOT_EXISTED, exception.getErrorCode());
    }

    @Test
    void updateUser_success() {
        String userId = "123";
        UserUpdateRequest request = new UserUpdateRequest();
        request.setPassword("newPassword");
        request.setRoles(List.of("roleId"));

        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateUser(any(), any());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findAllById(request.getRoles())).thenReturn(List.of(new Role()));
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toUserResponse(any())).thenReturn(new UserResponse());

        UserResponse response = userService.updateUser(userId, request);

        assertNotNull(response);
        verify(userRepository).save(any());
    }

    @Test
    void updateUser_userNotFound_throwsException() {
        String userId = "notFoundId";
        UserUpdateRequest request = new UserUpdateRequest();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> userService.updateUser(userId, request));
        assertEquals(ErrorCode.USER_NOT_EXISTED, exception.getErrorCode());
    }

}
