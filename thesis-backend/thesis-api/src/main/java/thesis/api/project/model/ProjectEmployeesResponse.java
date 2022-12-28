package thesis.api.project.model;

import thesis.domain.paging.PagingSettings;

import java.util.List;

public record ProjectEmployeesResponse(
        List<ProjectEmployeeResponse> employees,
        PagingSettings settings
) {
}
