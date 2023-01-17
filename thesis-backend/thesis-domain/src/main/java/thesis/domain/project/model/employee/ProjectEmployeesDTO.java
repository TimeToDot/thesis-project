package thesis.domain.project.model.employee;

import lombok.Builder;
import thesis.domain.paging.Paging;
import thesis.domain.paging.PagingSettings;
import thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectEmployeesDTO(
        List<ProjectEmployeeDTO> employees,
        Paging paging,
        Sorting sorting
) {
}
