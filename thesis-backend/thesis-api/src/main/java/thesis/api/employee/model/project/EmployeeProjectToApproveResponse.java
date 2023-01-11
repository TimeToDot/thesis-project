package thesis.api.employee.model.project;

public record EmployeeProjectToApproveResponse(
        EmployeeProjectResponse project,
        Integer count
) {
}
