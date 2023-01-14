package thesis.domain.employee.model;

import lombok.Builder;
import net.bytebuddy.TypeCache;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record EmployeeTasksDTO(
        List<EmployeeTaskDTO> tasks,
        Paging paging,
        Sorting sorting
) {
}
