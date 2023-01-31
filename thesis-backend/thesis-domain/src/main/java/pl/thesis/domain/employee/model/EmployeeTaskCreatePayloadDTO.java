package pl.thesis.domain.employee.model;

import java.util.Date;
import java.util.UUID;

public record EmployeeTaskCreatePayloadDTO(

        Date startDate,
        Date endDate,
        UUID projectId,
        UUID taskId
) {
}
