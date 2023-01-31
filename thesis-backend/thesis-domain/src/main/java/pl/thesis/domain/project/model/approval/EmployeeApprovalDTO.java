package pl.thesis.domain.project.model.approval;

import pl.thesis.domain.employee.model.SimplePositionDTO;

import java.util.UUID;

public record EmployeeApprovalDTO(
        UUID employeeId,
        String firstName,
        String lastName,
        String email,
        String imagePath,
        SimplePositionDTO position
) {
}
