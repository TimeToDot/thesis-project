package pl.thesis.domain.project.model.approval;

public record ProjectEmployeeApprovalDTO(
        Long id,
        ProjectApprovalDTO employee,
        Boolean active
) {
}
