package thesis.api.project.model.approval;

import thesis.api.project.model.employee.ProjectEmployee;

import java.util.Date;
import java.util.UUID;

public record ProjectApprovalResponse(
        UUID projectApprovalId,
        ProjectEmployee employee,
        ApprovalStatus status,
        Date lastRequest
) {
}
