package thesis.domain.employee.model;

import lombok.Builder;

import java.util.List;

@Builder
public record EmployeeTasksDTO(
        List<EmployeeTaskDTO> tasks
) {
}
