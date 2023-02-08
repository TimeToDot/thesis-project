package pl.thesis.api.project.model.approval;

import pl.thesis.api.employee.model.SimplePositionResponse;

import java.util.UUID;

public record EmployeeApprovalResponse(
        UUID employeeId,
        String firstName,
        String lastName,
        String email,
        String imagePath,
        SimplePositionResponse position
) {
}
