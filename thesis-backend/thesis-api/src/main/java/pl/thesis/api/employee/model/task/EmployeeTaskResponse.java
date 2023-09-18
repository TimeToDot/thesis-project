package pl.thesis.api.employee.model.task;

import pl.thesis.api.employee.model.project.EmployeeProjectResponse;

import java.util.Date;

public record EmployeeTaskResponse(
        Long id,
        Date startDate,
        Date endDate,
        EmployeeProjectResponse project,
        TaskResponse task,
        TaskStatus status
){

}
