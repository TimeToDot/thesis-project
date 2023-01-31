package pl.thesis.api.project.model.approval;

import pl.thesis.api.employee.model.SimplePositionResponse;
import pl.thesis.domain.employee.model.SimplePositionDTO;

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
