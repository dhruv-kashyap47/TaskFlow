package com.example.taskflow.repository;

import com.example.taskflow.model.Priority;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t " +
            "WHERE (:status IS NULL OR t.status = :status) " +
            "AND (:priority IS NULL OR t.priority = :priority) " +
            "AND (:assigneeId IS NULL OR t.assignee.id = :assigneeId) " +
            "AND (:projectId IS NULL OR t.project.id = :projectId)")
    Page<Task> search(@Param("status") TaskStatus status,
                      @Param("priority") Priority priority,
                      @Param("assigneeId") Long assigneeId,
                      @Param("projectId") Long projectId,
                      Pageable pageable);
}
