package thesis.api.employee.model.project;

import thesis.domain.paging.PagingSettings;

import java.util.Date;

public record EmployeeProjectsToApproveRequest(
        Date startDate,
        Date endDate
) {
}
