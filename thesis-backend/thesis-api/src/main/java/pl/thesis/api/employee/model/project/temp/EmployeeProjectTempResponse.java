package pl.thesis.api.employee.model.project.temp;

import java.util.Date;
import java.util.UUID;

public record EmployeeProjectTempResponse(
    ProjectTempResponse project,
    String employeeId,
    Date joinDate,

    Date exitDate
) {
}
