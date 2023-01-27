package thesis.api.employee.model.project.temp;

import thesis.data.project.model.Project;

import java.util.Date;
import java.util.UUID;

public record EmployeeProjectTempResponse(
    ProjectTempResponse project,
    UUID employeeId,
    Date joinDate,

    Date exitDate
) {
}
