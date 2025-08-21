package com.example.taskflow.config;

import com.example.taskflow.model.*;
import com.example.taskflow.repository.ProjectRepository;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepo, ProjectRepository projectRepo, TaskRepository taskRepo, PasswordEncoder encoder) {
        return args -> {
            // Create default admin if absent
            Optional<User> adminOpt = userRepo.findByEmail("admin@taskflow.dev");
            if (adminOpt.isEmpty()) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@taskflow.dev");
                admin.setPassword(encoder.encode("password"));
                admin.setRole(Role.ADMIN);
                userRepo.save(admin);
            }

            // Create default user if absent
            Optional<User> userOpt = userRepo.findByEmail("user@taskflow.dev");
            User defaultUser = userOpt.orElseGet(() -> {
                User u = new User();
                u.setName("Demo User");
                u.setEmail("user@taskflow.dev");
                u.setPassword(encoder.encode("password"));
                u.setRole(Role.USER);
                return userRepo.save(u);
            });

            // Seed a project and tasks if empty
            if (projectRepo.count() == 0) {
                Project p = new Project();
                p.setName("Onboarding Project");
                p.setDescription("Kick-off tasks for TaskFlow Pro");
                p.setOwner(defaultUser);
                projectRepo.save(p);

                Task t1 = new Task();
                t1.setTitle("Read the README");
                t1.setDescription("Get familiar with the project setup.");
                t1.setStatus(TaskStatus.TODO);
                t1.setPriority(Priority.MEDIUM);
                t1.setDueDate(LocalDate.now().plusDays(3));
                t1.setProject(p);
                t1.setAssignee(defaultUser);
                taskRepo.save(t1);

                Task t2 = new Task();
                t2.setTitle("Try the APIs");
                t2.setDescription("Use Swagger UI to create a project and tasks.");
                t2.setStatus(TaskStatus.IN_PROGRESS);
                t2.setPriority(Priority.HIGH);
                t2.setDueDate(LocalDate.now().plusDays(5));
                t2.setProject(p);
                t2.setAssignee(defaultUser);
                taskRepo.save(t2);
            }
        };
    }
}
