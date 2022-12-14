package thesis.api.employee.model;


import java.util.List;

public record EmployeeTasksResponse (
    List<EmployeeTask> employeeTasks
)
{
}
