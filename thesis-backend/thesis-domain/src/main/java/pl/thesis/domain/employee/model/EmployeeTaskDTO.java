package pl.thesis.domain.employee.model;

import lombok.Builder;
import pl.thesis.domain.task.model.TaskFormDto;
import pl.thesis.domain.task.model.TaskStatusDTO;

import java.util.Date;

@Builder
public record EmployeeTaskDTO(
        Long id,

        Long employeeId,

        Date startDate,
        Date endDate,
/*        LocalDateTime startTime,
        LocalDateTime endTime,*/
        EmployeeProjectDTO project,
        TaskFormDto task,
        TaskStatusDTO status
) {
}
