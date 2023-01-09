package thesis.api.employee.model.task;

import thesis.api.employee.model.project.EmployeeProjectResponse;
import thesis.data.task.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record EmployeeTaskResponse(
        UUID id,
        Date startDate,
        Date endDate,
        LocalDateTime startTime,
        LocalDateTime endTime,
        EmployeeProjectResponse employeeProjectResponse,
        TaskResponse task,
        TaskStatus status
){

}
