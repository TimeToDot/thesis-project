package pl.thesis.api.project.model.employee;

public record ProjectEmployeeUpdatePayload(
        String employeeId,
        Integer modifier,
        Integer workingTime,
        //RoleDTOStatus roleDTOStatus,
        Boolean active
) {
}
