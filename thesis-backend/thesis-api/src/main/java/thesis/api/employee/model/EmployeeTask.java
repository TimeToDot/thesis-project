package thesis.api.employee.model;

import java.util.UUID;

public record EmployeeTask(
        String id,
        String startDate,
        String endDate,
        String startTime,
        String endTime,
        UUID projectId,
        UUID taskId
){

}
