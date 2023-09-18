package pl.thesis.api.project.model.employee;

public record ProjectEmployeeCreatePayload(
        Long employeeId,
        Integer modifier,
        Integer workingTime
) {
}
