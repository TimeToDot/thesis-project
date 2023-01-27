package thesis.api.employee.model.project.temp;

import thesis.api.employee.model.project.EmployeeProjectDetailsResponse;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

public record EmployeeProjectsTempResponse(
        List<EmployeeProjectTempResponse> projects,
        Paging paging,
        Sorting sorting
) {
}
