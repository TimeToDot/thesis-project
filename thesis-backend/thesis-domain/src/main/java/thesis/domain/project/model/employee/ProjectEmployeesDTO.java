package thesis.domain.project.model.employee;

import thesis.domain.paging.PagingSettings;

import java.util.List;

public record ProjectEmployeesDTO(
        List<ProjectEmployeeDTO> employees,
        PagingSettings settings
) {
}
