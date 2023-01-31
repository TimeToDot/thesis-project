package pl.thesis.api.employee.model.task.temp;

import pl.thesis.api.employee.model.task.EmployeeTaskResponse;

import java.util.List;

public record EmployeeTasksTemp(
        List<EmployeeTaskTemp> tasks
) {
}
