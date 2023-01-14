package thesis.domain.employee.model;

import lombok.Builder;

@Builder
public record EmployeeProjectToApproveDTO(
        EmployeeProjectDTO project,
        Integer count
) {
}
