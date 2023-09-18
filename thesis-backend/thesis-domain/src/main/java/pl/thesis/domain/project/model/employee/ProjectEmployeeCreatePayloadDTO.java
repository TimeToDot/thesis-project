package pl.thesis.domain.project.model.employee;

public record ProjectEmployeeCreatePayloadDTO(

        Long projectId,
        Long employeeId,
        Integer modifier,
        Integer workingTime
) {
}
