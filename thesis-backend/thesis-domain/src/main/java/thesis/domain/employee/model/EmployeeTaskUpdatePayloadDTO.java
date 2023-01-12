package thesis.domain.employee.model;

import org.springframework.format.annotation.DateTimeFormat;
import thesis.domain.task.model.TaskStatusDTO;

import java.util.Date;
import java.util.UUID;

public record EmployeeTaskUpdatePayloadDTO(
        UUID id,
        @DateTimeFormat(pattern = "YYYY-MM-DD")
        Date startDate,
        @DateTimeFormat(pattern = "YYYY-MM-DD")
        Date endDate,
/*        @DateTimeFormat(pattern = "HH-mm")
        LocalDateTime startTime,
        @DateTimeFormat(pattern = "HH-mm")
        LocalDateTime endTime,*/
        UUID projectId,
        UUID taskId,
        TaskStatusDTO status
) {
}
