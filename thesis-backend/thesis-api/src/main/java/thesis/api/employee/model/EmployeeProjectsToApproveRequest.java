package thesis.api.employee.model;

import thesis.domain.paging.PagingSettings;

import java.util.Date;

public record EmployeeProjectsToApproveRequest(
        Date startDate,
        Date endDate,
        PagingSettings settings
) {
}
