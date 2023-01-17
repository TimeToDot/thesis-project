package thesis.api.employee.model.task;

import org.springframework.format.annotation.DateTimeFormat;
import thesis.domain.task.model.TaskStatusDTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record EmployeeTaskUpdatePayload(
        UUID id,
        //@DateTimeFormat(pattern = "YYYY-MM-DD")
        Date startDate,
        //@DateTimeFormat(pattern = "YYYY-MM-DD")
        Date endDate,
/*        @DateTimeFormat(pattern = "HH-mm")
        LocalDateTime startTime,
        @DateTimeFormat(pattern = "HH-mm")
        LocalDateTime endTime,*/
        UUID projectId,
        UUID taskId,

        TaskStatus status
) {
}
