package pl.thesis.api.employee.model.project.temp;

import java.util.Date;

public record EmployeeProjectTempResponse(
    ProjectTempResponse project,
    String employeeId,
    Date joinDate,

    Date exitDate
) {
}
