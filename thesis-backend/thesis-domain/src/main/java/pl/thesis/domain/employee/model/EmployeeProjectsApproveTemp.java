package pl.thesis.domain.employee.model;

import java.util.List;

public record EmployeeProjectsApproveTemp(
        Long id,
        List<Long> projects
) {
}
