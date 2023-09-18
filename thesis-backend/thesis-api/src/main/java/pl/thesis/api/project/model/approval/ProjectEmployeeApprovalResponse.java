package pl.thesis.api.project.model.approval;

public record ProjectEmployeeApprovalResponse(
        Long id,
        ProjectApprovalResponse employee,
        Boolean active
) {
}
