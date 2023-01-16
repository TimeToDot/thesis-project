package thesis.domain.project.model.approval;

import thesis.domain.project.model.employee.EmployeeDTO;

import java.util.Date;
import java.util.UUID;

public record ProjectApprovalDTO(
        UUID projectApprovalId,
        EmployeeDTO employee,
        ApprovalStatus status,
        Date lastRequest
) {
}
