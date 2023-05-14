package com.lgzarturo.api.personal.api.task;

import com.lgzarturo.api.personal.api.generic.CrudService;
import com.lgzarturo.api.personal.api.task.dto.TaskRequest;
import com.lgzarturo.api.personal.api.task.dto.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TaskService extends CrudService<TaskRequest, TaskResponse, Long> {
    Page<TaskResponse> findAll(TaskStatus status, Boolean finished, LocalDateTime estimatedAt, Pageable pageable);
    TaskResponse updateStatus(Long id, TaskStatus status);
    TaskResponse setUnfinished(Long id);
    TaskResponse setFinished(Long id);
    TaskResponse setEstimatedAt(Long id, LocalDateTime estimatedAt);
}
