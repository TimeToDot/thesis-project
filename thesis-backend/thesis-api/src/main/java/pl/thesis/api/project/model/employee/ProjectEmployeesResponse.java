package pl.thesis.api.project.model.employee;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectEmployeesResponse(
        List<ProjectEmployeeResponse> employees,
        Paging paging,
        Sorting sorting
) {
}
