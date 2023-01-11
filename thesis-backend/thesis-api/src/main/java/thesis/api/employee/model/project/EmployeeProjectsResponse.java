package thesis.api.employee.model.project;

import lombok.Builder;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record EmployeeProjectsResponse(
        List<EmployeeProjectResponse> projects,
        Paging paging,
        Sorting sorting
) {

}
