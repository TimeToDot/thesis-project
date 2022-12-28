package thesis.api.employee.model;

import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

public record EmployeeProjectsToApproveResponse(
        List<EmployeeProjectToApproveResponse> projects,
        Paging paging,
        Sorting sorting
) {
}
