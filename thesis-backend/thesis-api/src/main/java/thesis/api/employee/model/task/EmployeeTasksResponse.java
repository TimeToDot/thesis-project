package thesis.api.employee.model.task;


import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

public record EmployeeTasksResponse (
    List<EmployeeTaskResponse> tasks
)
{
}
