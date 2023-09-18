package pl.thesis.domain.employee.model;

import java.util.Date;

public record EmployeeTaskCreatePayloadDTO(

        Date startDate,
        Date endDate,
        Long projectId,
        Long taskId
) {
}
