package thesis.api.employee.model.task;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record EmployeeTaskCreatePayload(
        @DateTimeFormat(pattern = "YYYY-MM-DD-HH-mm")
        Date startDate,
        @DateTimeFormat(pattern = "YYYY-MM-DD-HH-mm")
        Date endDate,
        UUID projectId,
        UUID taskId
) {
}
