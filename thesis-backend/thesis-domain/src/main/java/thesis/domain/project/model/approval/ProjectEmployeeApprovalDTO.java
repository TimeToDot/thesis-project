package thesis.domain.project.model.approval;

import thesis.data.project.model.Project;
import thesis.domain.project.model.approval.ProjectApprovalDTO;

import java.util.Date;
import java.util.UUID;

public record ProjectEmployeeApprovalDTO(
        UUID id,
        ProjectApprovalDTO employee,
        Boolean active
) {
}
