package pl.thesis.domain.employee.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.thesis.domain.task.model.TaskStatusDTO;

import java.util.Date;

public record EmployeeTaskUpdatePayloadDTO(
        Long id,
        Long employeeId,
        @DateTimeFormat(pattern = "YYYY-MM-DD")
        Date startDate,
        @DateTimeFormat(pattern = "YYYY-MM-DD")
        Date endDate,
        Long projectId,
        Long taskId,
        TaskStatusDTO status
) {
}
