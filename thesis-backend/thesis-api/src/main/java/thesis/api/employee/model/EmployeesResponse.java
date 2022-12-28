package thesis.api.employee.model;

import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

public record EmployeesResponse(
        List<EmployeeResponse> employees,
        Paging paging,
        Sorting sorting

) {
}
