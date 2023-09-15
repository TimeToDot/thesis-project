package pl.thesis.domain.employee.model;

import pl.thesis.domain.task.model.TaskStatusDTO;

public record EmployeeTaskDeletePayloadDTO(
        Long id,
        Long projectId,
        Long taskId,
        TaskStatusDTO status
) {
}
