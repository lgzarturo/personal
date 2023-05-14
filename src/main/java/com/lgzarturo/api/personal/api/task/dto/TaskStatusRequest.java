package com.lgzarturo.api.personal.api.task.dto;

import com.lgzarturo.api.personal.api.task.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaskStatusRequest {
    @NotNull
    private TaskStatus status;
}
