package com.example.taskflow.service;

import com.example.taskflow.dto.TaskRequest;
import com.example.taskflow.model.Priority;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<Task> list(TaskStatus status, Priority priority, Long assigneeId, Long projectId, Pageable pageable);
    Task create(TaskRequest request);
    Task update(Long id, TaskRequest request);
    void delete(Long id);
    Task get(Long id);
}
