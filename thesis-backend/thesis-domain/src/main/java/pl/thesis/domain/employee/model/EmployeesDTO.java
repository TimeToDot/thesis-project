package pl.thesis.domain.employee.model;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record EmployeesDTO(
        List<EmployeeDTO> employees,
        Paging paging,
        Sorting sorting
) {
}
