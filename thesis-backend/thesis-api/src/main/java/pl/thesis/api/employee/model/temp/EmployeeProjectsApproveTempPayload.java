package pl.thesis.api.employee.model.temp;

import java.util.List;
import java.util.UUID;

public record EmployeeProjectsApproveTempPayload(
        List<String> projects
) {
}
