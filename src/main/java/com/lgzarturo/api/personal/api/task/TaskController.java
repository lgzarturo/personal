package com.lgzarturo.api.personal.api.task;

import com.lgzarturo.api.personal.api.generic.CrudController;
import com.lgzarturo.api.personal.api.task.dto.TaskRequest;
import com.lgzarturo.api.personal.api.task.dto.TaskResponse;
import com.lgzarturo.api.personal.api.task.dto.TaskStatusRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/tasks")
@AllArgsConstructor
@Slf4j
public class TaskController implements CrudController<TaskResponse, TaskRequest, Long> {

    private final TaskService taskService;

    @GetMapping
    public Page<TaskResponse> getAll(
        @RequestParam(required = false) TaskStatus status,
        @RequestParam(required = false) Boolean finished,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        @RequestParam(required = false) LocalDateTime estimatedAt,
        Pageable pageable
        ) {
        return taskService.findAll(status, finished, estimatedAt, pageable);
    }

    @PostMapping
    @Override
    public ResponseEntity<TaskResponse> post(@RequestBody @Valid TaskRequest request) {
        var taskResponse = taskService.create(request);
        return ResponseEntity.created(getLocation( taskResponse.getId())).body(taskResponse);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<TaskResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.read(id));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<TaskResponse> put(@PathVariable Long id, @RequestBody @Valid TaskRequest request) {
        return ResponseEntity.accepted().body(taskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id, @RequestBody TaskStatusRequest request) {
        return ResponseEntity.accepted().body(taskService.updateStatus(id, request.getStatus()));
    }

    @PatchMapping("/{id}/unfinished")
    public ResponseEntity<TaskResponse> setUnfinished(@PathVariable Long id) {
        return ResponseEntity.accepted().body(taskService.setUnfinished(id));
    }

    @PatchMapping("/{id}/finished")
    public ResponseEntity<Void> setFinished(@PathVariable Long id) {
        taskService.setFinished(id);
        return ResponseEntity.accepted().location(getLocation(id)).build();
    }

    @PatchMapping("/{id}/estimated-at")
    public ResponseEntity<TaskResponse> setEstimatedAt(@PathVariable Long id, @RequestBody TaskRequest request) {
        return ResponseEntity.accepted().body(taskService.setEstimatedAt(id, request.getEstimatedAt()));
    }

    private URI getLocation(Long id) {
        return URI.create(String.format("/api/v1/tasks/%s", id));
    }
}
