package thesis.api.employee.model;

public record EmployeeProjectToApproveResponse(
        EmployeeProjectResponse project,
        Integer count
) {
}
