package com.example.taskflow.service.impl;

import com.example.taskflow.dto.TaskRequest;
import com.example.taskflow.model.*;
import com.example.taskflow.repository.ProjectRepository;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Task> list(TaskStatus status, Priority priority, Long assigneeId, Long projectId, Pageable pageable) {
        return taskRepository.search(status, priority, assigneeId, projectId, pageable);
    }

    @Override
    public Task create(TaskRequest request) {
        Task t = new Task();
        t.setTitle(request.getTitle());
        t.setDescription(request.getDescription());
        if (request.getStatus() != null) t.setStatus(request.getStatus());
        if (request.getPriority() != null) t.setPriority(request.getPriority());
        t.setDueDate(request.getDueDate());
        if (request.getProjectId() != null) {
            Project p = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            t.setProject(p);
        }
        if (request.getAssigneeId() != null) {
            User u = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            t.setAssignee(u);
        }
        return taskRepository.save(t);
    }

    @Override
    public Task update(Long id, TaskRequest request) {
        Task t = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        if (request.getTitle() != null) t.setTitle(request.getTitle());
        if (request.getDescription() != null) t.setDescription(request.getDescription());
        if (request.getStatus() != null) t.setStatus(request.getStatus());
        if (request.getPriority() != null) t.setPriority(request.getPriority());
        if (request.getDueDate() != null) t.setDueDate(request.getDueDate());
        if (request.getProjectId() != null) {
            Project p = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            t.setProject(p);
        }
        if (request.getAssigneeId() != null) {
            User u = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            t.setAssignee(u);
        }
        return taskRepository.save(t);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task get(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }
}
