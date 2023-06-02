package com.lgzarturo.api.personal.api.task;

import com.lgzarturo.api.personal.api.task.dto.TaskRequest;
import com.lgzarturo.api.personal.api.task.dto.TaskResponse;
import com.lgzarturo.api.personal.api.task.mapper.TaskMapper;
import com.lgzarturo.api.personal.exceptions.ResourceNotFoundException;
import com.lgzarturo.api.personal.exceptions.ResourceValidationException;
import com.lgzarturo.api.personal.utils.Helpers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Slf4j
public class TaskServiceJpa implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Page<TaskResponse> findAll(TaskStatus status, Boolean finished, LocalDateTime estimatedAt, Pageable pageable) {
        if (status != null && finished != null && estimatedAt != null)
            return taskRepository.findAllByStatusAndFinishedAndEstimatedAt(status, finished, estimatedAt, pageable)
                    .map(TaskMapper.INSTANCE::mapToResponse);
        else if (status != null && finished != null)
            return taskRepository.findAllByStatusAndFinished(status, finished, pageable)
                    .map(TaskMapper.INSTANCE::mapToResponse);
        else if (status != null && estimatedAt != null)
            return taskRepository.findAllByStatusAndEstimatedAt(status, estimatedAt, pageable)
                    .map(TaskMapper.INSTANCE::mapToResponse);
        else if (finished != null && estimatedAt != null)
            return taskRepository.findAllByFinishedAndEstimatedAt(finished, estimatedAt, pageable)
                    .map(TaskMapper.INSTANCE::mapToResponse);
        else if (status != null)
            return taskRepository.findAllByStatus(status, pageable)
                    .map(TaskMapper.INSTANCE::mapToResponse);
        else if (finished != null)
            return taskRepository.findAllByFinished(finished, pageable)
                    .map(TaskMapper.INSTANCE::mapToResponse);
        else if (estimatedAt != null)
            return taskRepository.findAllByEstimatedAt(estimatedAt, pageable)
                    .map(TaskMapper.INSTANCE::mapToResponse);
        else
            return taskRepository.findAll(pageable)
                    .map(TaskMapper.INSTANCE::mapToResponse);
    }

    @Transactional
    @Override
    public TaskResponse create(TaskRequest request) {
        var task = TaskMapper.INSTANCE.mapToEntity(request);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.ON_TIME);
        task.setFinished(false);
        task = taskRepository.save(task);
        return TaskMapper.INSTANCE.mapToResponse(task);
    }

    @Override
    public TaskResponse read(Long id) {
        var task = getById(id);
        return TaskMapper.INSTANCE.mapToResponse(task);
    }

    @Transactional
    @Override
    public TaskResponse update(Long id, TaskRequest request) {
        var task = getById(id);
        Helpers.copyNonNullProperties(request, task);
        task.setLastModifiedDate(LocalDateTime.now());
        return TaskMapper.INSTANCE.mapToResponse(task);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        var task = getById(id);
        taskRepository.delete(task);
    }

    @Transactional
    @Override
    public TaskResponse updateStatus(Long id, TaskStatus status) {
        var task = getById(id);
        task.setStatus(status);
        task.setLastModifiedDate(LocalDateTime.now());
        return TaskMapper.INSTANCE.mapToResponse(task);
    }

    @Transactional
    @Override
    public TaskResponse setUnfinished(Long id) {
        var task = getById(id);
        task.setFinished(false);
        task.setLastModifiedDate(LocalDateTime.now());
        return TaskMapper.INSTANCE.mapToResponse(task);
    }

    @Transactional
    @Override
    public TaskResponse setFinished(Long id) {
        var task = getById(id);
        taskRepository.setFinished(id);
        return TaskMapper.INSTANCE.mapToResponse(task);
    }

    @Transactional
    @Override
    public TaskResponse setEstimatedAt(Long id, LocalDateTime estimatedAt) {
        var task = getById(id);
        if (estimatedAt.isBefore(task.getCreatedAt())) {
            throw new ResourceValidationException("Estimated at must be after created at");
        }
        if (task.getFinished() && estimatedAt.isAfter(task.getEstimatedAt())) {
            throw new ResourceValidationException("Estimated at must be before finished at");
        }
        var today = LocalDateTime.now();
        if (estimatedAt.isBefore(today)) {
            task.setStatus(TaskStatus.DELAYED);
        } else if (estimatedAt.isEqual(today)) {
            task.setStatus(TaskStatus.ON_TIME);
        } else {
            task.setStatus(TaskStatus.AHEAD);
        }
        task.setEstimatedAt(estimatedAt);
        task.setLastModifiedDate(LocalDateTime.now());
        return TaskMapper.INSTANCE.mapToResponse(task);
    }

    private Task getById(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }
}
