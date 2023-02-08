package pl.thesis.domain.project.model.employee;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectEmployeesDTO(
        List<ProjectEmployeeDTO> employees,
        Paging paging,
        Sorting sorting
) {
}
