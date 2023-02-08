package pl.thesis.domain.employee.model;

import java.util.List;
import java.util.UUID;

public record EmployeeProjectsApproveTemp(
        UUID id,
        List<UUID> projects
) {
}
