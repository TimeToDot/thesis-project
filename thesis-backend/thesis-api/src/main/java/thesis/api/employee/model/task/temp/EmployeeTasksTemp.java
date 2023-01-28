package thesis.api.employee.model.task.temp;

import thesis.api.employee.model.task.EmployeeTaskResponse;

import java.util.List;

public record EmployeeTasksTemp(
        List<EmployeeTaskTemp> tasks
) {
}
