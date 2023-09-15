package pl.thesis.domain.project.model.approval;

import pl.thesis.domain.employee.model.SimplePositionDTO;

public record EmployeeApprovalDTO(
        Long employeeId,
        String firstName,
        String lastName,
        String email,
        String imagePath,
        SimplePositionDTO position
) {
}
