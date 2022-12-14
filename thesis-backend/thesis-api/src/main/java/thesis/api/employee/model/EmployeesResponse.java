package thesis.api.employee.model;

import java.util.List;

public record EmployeesResponse(
        List<EmployeeResponse> employeeResponses
) {
}
