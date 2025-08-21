package com.example.taskflow.controller;

import com.example.taskflow.dto.TaskRequest;
import com.example.taskflow.model.Priority;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskStatus;
import com.example.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<Task>> list(@RequestParam(required = false) TaskStatus status,
                                           @RequestParam(required = false) Priority priority,
                                           @RequestParam(required = false) Long assigneeId,
                                           @RequestParam(required = false) Long projectId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id,desc") String sort) {

        String[] sp = sort.split(",");
        Sort s = Sort.by(Sort.Direction.fromString(sp.length > 1 ? sp[1] : "desc"), sp[0]);
        Pageable pageable = PageRequest.of(page, size, s);
        return ResponseEntity.ok(taskService.list(status, priority, assigneeId, projectId, pageable));
    }

    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody TaskRequest req) {
        return ResponseEntity.ok(taskService.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody TaskRequest req) {
        return ResponseEntity.ok(taskService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
