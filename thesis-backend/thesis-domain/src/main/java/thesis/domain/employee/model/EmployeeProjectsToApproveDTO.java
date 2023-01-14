package thesis.domain.employee.model;

import lombok.Builder;

import java.util.List;

@Builder
public record EmployeeProjectsToApproveDTO(
        List<EmployeeProjectToApproveDTO> projects
) {
}
