package pl.thesis.domain.project.model.approval;

import java.util.UUID;

public record ProjectEmployeeApprovalDTO(
        UUID id,
        ProjectApprovalDTO employee,
        Boolean active
) {
}
