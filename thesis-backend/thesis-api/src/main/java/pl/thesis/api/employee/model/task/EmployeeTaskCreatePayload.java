package pl.thesis.api.employee.model.task;

import java.util.Date;

public record EmployeeTaskCreatePayload(
        //@JsonFormat(pattern = "YYYY-MM-DD HH:mm")
        Date startDate,
        //@JsonFormat(pattern = "YYYY-MM-DD HH:mm")
        Date endDate,
        String projectId,
        String taskId
) {
}
