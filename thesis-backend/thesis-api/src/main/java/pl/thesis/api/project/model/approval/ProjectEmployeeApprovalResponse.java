package pl.thesis.api.project.model.approval;

import java.util.UUID;

public record ProjectEmployeeApprovalResponse(
        UUID id,
        ProjectApprovalResponse employee,
        Boolean active
) {
}
