package com.example.taskflow.service.impl;

import com.example.taskflow.dto.ProjectRequest;
import com.example.taskflow.model.Project;
import com.example.taskflow.model.User;
import com.example.taskflow.repository.ProjectRepository;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Project> list(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Project create(ProjectRequest request) {
        Project p = new Project();
        p.setName(request.getName());
        p.setDescription(request.getDescription());
        if (request.getOwnerId() != null) {
            User owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            p.setOwner(owner);
        }
        return projectRepository.save(p);
    }

    @Override
    public Project update(Long id, ProjectRequest request) {
        Project p = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        if (request.getName() != null) p.setName(request.getName());
        if (request.getDescription() != null) p.setDescription(request.getDescription());
        if (request.getOwnerId() != null) {
            User owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            p.setOwner(owner);
        }
        return projectRepository.save(p);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project get(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
