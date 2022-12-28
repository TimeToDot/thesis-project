package thesis.api.employee.model;

import thesis.domain.paging.PagingSettings;

public record EmployeeTasksRequest(
        String startDate,
        String endDate,
        PagingSettings settings
) {
}
