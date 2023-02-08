package pl.thesis.api.employee.model.task;


import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

public record EmployeeTasksResponse (
    List<EmployeeTaskResponse> tasks,
    Paging paging,
    Sorting sorting
)
{
}
