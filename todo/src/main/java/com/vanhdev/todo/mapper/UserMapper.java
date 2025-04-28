package com.vanhdev.todo.mapper;

import com.vanhdev.todo.dto.request.UserCreationRequest;
import com.vanhdev.todo.dto.request.UserUpdateRequest;
import com.vanhdev.todo.dto.response.UserResponse;
import com.vanhdev.todo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
