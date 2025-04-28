package com.vanhdev.todo.dto.request;

import com.vanhdev.todo.constant.Priority;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequest {
     String userId;
     String categoryId;
     String title;
     String description;
     boolean completed;
     Priority priority;
     LocalDateTime dueDate;
}
