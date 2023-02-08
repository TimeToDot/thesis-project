package pl.thesis.domain.employee.model;

import pl.thesis.domain.task.model.TaskStatusDTO;

import java.util.UUID;

public record EmployeeTaskDeletePayloadDTO(
        UUID id,
        UUID projectId,
        UUID taskId,
        TaskStatusDTO status
) {
}
