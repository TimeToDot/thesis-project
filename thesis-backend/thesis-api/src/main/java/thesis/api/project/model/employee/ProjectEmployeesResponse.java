package thesis.api.project.model.employee;

import thesis.domain.paging.PagingSettings;

import java.util.List;

public record ProjectEmployeesResponse(
        List<ProjectEmployeeResponse> employees,
        PagingSettings settings
) {
}
