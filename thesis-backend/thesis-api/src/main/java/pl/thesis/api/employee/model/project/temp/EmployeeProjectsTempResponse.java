package pl.thesis.api.employee.model.project.temp;

import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

public record EmployeeProjectsTempResponse(
        List<EmployeeProjectTempResponse> projects,
        Paging paging,
        Sorting sorting
) {
}
