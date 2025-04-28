package com.vanhdev.todo.dto.response;

import com.vanhdev.todo.constant.Priority;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {
     String id;
     String title;
     String description;
     boolean completed;
     Priority priority;
     LocalDateTime dueDate;
     LocalDateTime createdAt;
     LocalDateTime updatedAt;
     CategoryResponse category;
     UserResponse user;
}
