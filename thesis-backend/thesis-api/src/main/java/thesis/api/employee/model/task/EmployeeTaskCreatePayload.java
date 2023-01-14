package thesis.api.employee.model.task;

import java.util.Date;
import java.util.UUID;

public record EmployeeTaskCreatePayload(
        //@JsonFormat(pattern = "YYYY-MM-DD HH:mm")
        Date startDate,
        //@JsonFormat(pattern = "YYYY-MM-DD HH:mm")
        Date endDate,
        UUID projectId,
        UUID taskId
) {
}
