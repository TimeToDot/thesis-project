package thesis.domain.employee.model;

import lombok.Builder;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record EmployeeProjectsDTO (
        List<EmployeeProjectDTO> employeeProjectDTOList,
        Paging paging,
        Sorting sorting
){

}
