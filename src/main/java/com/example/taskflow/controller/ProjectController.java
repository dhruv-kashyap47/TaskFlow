package com.example.taskflow.controller;

import com.example.taskflow.dto.ProjectRequest;
import com.example.taskflow.model.Project;
import com.example.taskflow.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<Page<Project>> list(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id,desc") String sort) {
        String[] sp = sort.split(",");
        Sort s = Sort.by(Sort.Direction.fromString(sp[1]), sp[0]);
        Pageable pageable = PageRequest.of(page, size, s);
        return ResponseEntity.ok(projectService.list(pageable));
    }

    @PostMapping
    public ResponseEntity<Project> create(@Valid @RequestBody ProjectRequest req) {
        return ResponseEntity.ok(projectService.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> get(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody ProjectRequest req) {
        return ResponseEntity.ok(projectService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
