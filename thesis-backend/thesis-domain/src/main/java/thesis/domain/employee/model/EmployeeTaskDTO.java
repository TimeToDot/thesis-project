package thesis.domain.employee.model;

import lombok.Builder;
import thesis.data.task.model.TaskStatus;
import thesis.domain.task.model.TaskFormDto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Builder
public record EmployeeTaskDTO(
        UUID id,
        Date startDate,
        Date endDate,
        LocalDateTime startTime,
        LocalDateTime endTime,
        EmployeeProjectDTO project,
        TaskFormDto task,
        TaskStatus status
) {
}
