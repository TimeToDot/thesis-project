package thesis.api.employee.model.project.temp;

import java.util.List;
import java.util.UUID;

public record EmployeeProjectsApproveTemp(
        List<UUID> projects
) {
}
