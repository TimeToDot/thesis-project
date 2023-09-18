package pl.thesis.domain.project.model.employee;

public record ProjectEmployeeUpdatePayloadDTO(

        Long projectId,
        Long projectEmployeeId,
        Integer modifier,
        Integer workingTime,
        //RoleDTOStatus roleDTOStatus,
        Boolean active
) {
}
