package com.lgzarturo.api.personal.api.task.mapper;

import com.lgzarturo.api.personal.api.task.Task;
import com.lgzarturo.api.personal.api.task.dto.TaskRequest;
import com.lgzarturo.api.personal.api.task.dto.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskResponse mapToResponse(Task task);
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "finished", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Task mapToEntity(TaskRequest request);
}
