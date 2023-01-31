package pl.thesis.api.employee.model.project;

import java.util.Date;

public record EmployeeProjectsToApproveRequest(
        Date startDate,
        Date endDate
) {
}
