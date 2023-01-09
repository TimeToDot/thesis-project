package thesis.api.employee.model.task;

import thesis.domain.paging.PagingSettings;

import java.util.Date;

public record EmployeeTasksRequest(
        Date startDate,
        Date endDate,
        PagingSettings settings
) {
}
