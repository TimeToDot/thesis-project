package thesis.api.employee.model;

import lombok.Builder;

import java.util.List;

@Builder
public record EmployeeProjectsResponse(
        List<Project> projects
) {

}
