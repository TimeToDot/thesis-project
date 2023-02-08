package pl.thesis.api.employee.model.temp;

import java.util.List;

public record EmployeeProjectsApproveTempPayload(
        List<String> projects
) {
}
