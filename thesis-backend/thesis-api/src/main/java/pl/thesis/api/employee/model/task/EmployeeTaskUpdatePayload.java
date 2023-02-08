package pl.thesis.api.employee.model.task;

import java.util.Date;

public record EmployeeTaskUpdatePayload(
        String id,
        Date startDate,
        Date endDate,
        String projectId,
        String taskId,

        TaskStatus status
) {
}
