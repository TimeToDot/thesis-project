package pl.thesis.api.employee.model.task;

import pl.thesis.api.employee.model.project.EmployeeProjectResponse;
import pl.thesis.data.task.model.TaskStatus;

import java.util.Date;
import java.util.UUID;

public record EmployeeTaskResponse(
        UUID id,
        Date startDate,
        Date endDate,
        EmployeeProjectResponse project,
        TaskResponse task,
        TaskStatus status
){

}
