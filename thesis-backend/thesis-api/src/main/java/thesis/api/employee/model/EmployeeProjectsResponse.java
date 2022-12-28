package thesis.api.employee.model;

import lombok.Builder;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record EmployeeProjectsResponse(
        List<EmployeeProjectResponse> employeeProjectResponses,
        Paging paging,
        Sorting sorting
) {

}
