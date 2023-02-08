package pl.thesis.api.employee.model;

import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

public record EmployeesResponse(
        List<EmployeeResponse> employees,
        Paging paging,
        Sorting sorting

) {
}
