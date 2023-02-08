package pl.thesis.api.employee.model.project;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record EmployeeProjectsResponse(
        List<EmployeeProjectDetailsResponse> projects,
        Paging paging,
        Sorting sorting
) {

}
