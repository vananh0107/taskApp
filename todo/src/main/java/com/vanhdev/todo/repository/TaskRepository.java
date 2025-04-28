package com.vanhdev.todo.repository;

import com.vanhdev.todo.entity.Task;
import com.vanhdev.todo.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    Page<Task> findByUser(User user, Pageable pageable);
}
