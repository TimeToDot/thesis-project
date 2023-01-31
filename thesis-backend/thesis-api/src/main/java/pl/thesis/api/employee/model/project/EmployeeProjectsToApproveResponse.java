package pl.thesis.api.employee.model.project;

import java.util.List;

public record EmployeeProjectsToApproveResponse(
        List<EmployeeProjectToApproveResponse> projects
) {
}
