package pl.thesis.api.project.model.approval;

import pl.thesis.api.employee.model.SimplePositionResponse;

public record EmployeeApprovalResponse(
        Long employeeId,
        String firstName,
        String lastName,
        String email,
        String imagePath,
        SimplePositionResponse position
) {
}
