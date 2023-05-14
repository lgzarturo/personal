package com.lgzarturo.api.personal.api.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByStatus(TaskStatus status, Pageable pageable);
    Page<Task> findAllByFinished(Boolean finished, Pageable pageable);
    Page<Task> findAllByEstimatedAt(LocalDateTime estimatedAt, Pageable pageable);
    Page<Task> findAllByStatusAndFinished(TaskStatus status, Boolean finished, Pageable pageable);
    Page<Task> findAllByStatusAndEstimatedAt(TaskStatus status, LocalDateTime estimatedAt, Pageable pageable);
    Page<Task> findAllByStatusAndFinishedAndEstimatedAt(TaskStatus status, Boolean finished, LocalDateTime estimatedAt, Pageable pageable);
    Page<Task> findAllByFinishedAndEstimatedAt(Boolean finished, LocalDateTime estimatedAt, Pageable pageable);
    @Modifying
    @Query("UPDATE tasks t SET t.finished = true, t.lastModifiedDate = CURRENT_TIMESTAMP WHERE t.id = :id")
    void setFinished(Long id);
}
