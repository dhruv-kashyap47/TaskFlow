package com.example.taskflow.service;

import com.example.taskflow.dto.ProjectRequest;
import com.example.taskflow.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Page<Project> list(Pageable pageable);
    Project create(ProjectRequest request);
    Project update(Long id, ProjectRequest request);
    void delete(Long id);
    Project get(Long id);
}
