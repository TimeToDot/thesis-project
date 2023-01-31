package pl.thesis.domain.employee.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.thesis.domain.task.model.TaskStatusDTO;

import java.util.Date;
import java.util.UUID;

public record EmployeeTaskUpdatePayloadDTO(
        UUID id,
        UUID employeeId,
        @DateTimeFormat(pattern = "YYYY-MM-DD")
        Date startDate,
        @DateTimeFormat(pattern = "YYYY-MM-DD")
        Date endDate,
        UUID projectId,
        UUID taskId,
        TaskStatusDTO status
) {
}
